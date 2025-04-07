"""
This script parses PHP migration documentation XML files (incompatible.xml and deprecated.xml)
from different PHP versions and extracts structured data about breaking changes and deprecated features.

Source:
Visit https://github.com/php/doc-en/tree/master/appendices to download the official PHP migration notes.
Place the extracted migration folders (e.g., migration80, migration81, ...) under the 'appendices' directory.

The script will generate structured JSON files such as:
- php8_0-breaking-changes.json
- php8_0-deprecated-features.json

These files will contain categorized information like reserved keywords, removed functions, behavior changes, etc.,
and can be used for automated PHP code compatibility analysis.
"""
import os
import json
import re
from lxml import etree as ET

BASE_DIR = os.path.dirname(__file__)
APPENDICES_DIR = os.path.join(BASE_DIR, "appendices")


def version_from_dirname(dirname: str) -> str:
    if not dirname.startswith("migration"):
        raise ValueError(f"Unexpected directory name: {dirname}")

    version_str = dirname[len("migration"):]  # e.g., "80" or "83"

    if len(version_str) == 2:
        major, minor = version_str[0], version_str[1]
    elif len(version_str) == 3:  # Just in case PHP 8.10 or higher
        major, minor = version_str[0], version_str[1:]
    else:
        raise ValueError(f"Invalid version format in dirname: {dirname}")

    return f"php{major}_{minor}"


def get_text(elem):
    return ''.join(elem.itertext()).strip()


def get_section_title_text(para_elem):
    sect2_elem = para_elem.getparent()
    for i in range(3):
        if sect2_elem is not None and sect2_elem.tag != 'sect2':
            sect2_elem = sect2_elem.getparent()

    if sect2_elem is not None:
        title_elem = sect2_elem.find('title')
        return title_elem.text if title_elem is not None else None

    return None


def contains_reserved_phrase(para_elem):
    return re.search(r'is now a reserved (keyword|word)', get_text(para_elem), re.IGNORECASE)


def contains_removed_function_phrase(para_elem):
    if para_elem.find("function") is not None:
        return "has been removed" in get_text(para_elem).lower()
    return False


def contains_behavior_change(para_elem):
    text = get_text(para_elem).lower()
    return any(kw in text for kw in [
        "now throws", "previously", "behavior has changed", "converted into", "now results in",
        "is now", "default has changed", "used to", "will now"
    ])


def contains_removed_syntax_phrase(para_elem):
    return "has been removed" in get_text(para_elem).lower()


def contains_deprecated_function_phrase(para_elem):
    if para_elem.find("function") is not None:
        return "deprecated" in get_text(para_elem).lower()
    return False


def classify_breaking_changes(para_elem, output):
    title_text = get_section_title_text(para_elem)
    para_text = get_text(para_elem)

    if contains_reserved_phrase(para_elem):
        key_elem = para_elem.find("literal")
        key = key_elem.text if key_elem is not None else "unknown"
        output["reserved_keywords"].append({
            "key": key,
            "description": para_text,
            "related": title_text
        })
    elif contains_removed_function_phrase(para_elem):
        key_elem = para_elem.find("function")
        key = key_elem.text if key_elem is not None else "unknown_function"
        output["removed_functions"].append({
            "key": key,
            "description": para_text,
            "related": title_text
        })
    elif contains_behavior_change(para_elem):
        output["behavior_changes"].append({
            "key": "DEFINE_MANUALLY",
            "description": para_text,
            "related": title_text
        })
    elif contains_removed_syntax_phrase(para_elem):
        key_elem = para_elem.find("function") if para_elem.find("function") else para_elem.find("literal")
        key = key_elem.text if key_elem is not None else "unknown"
        output["removed_syntax"].append({
            "key": key,
            "description": para_text,
            "related": title_text
        })
    else:
        output["others"].append({
            "key": None,
            "description": para_text,
            "related": title_text
        })


def classify_deprecated_features(para_elem, output):
    title_text = get_section_title_text(para_elem)
    para_text = get_text(para_elem)

    if contains_deprecated_function_phrase(para_elem):
        key_elem = para_elem.find("function")
        key = key_elem.text if key_elem is not None else "unknown_function"
        output["deprecated_function"].append({
            "key": key,
            "description": para_text,
            "related": title_text
        })
    elif contains_removed_syntax_phrase(para_elem):
        key_elem = para_elem.find("function") if para_elem.find("function") else para_elem.find("literal")
        key = key_elem.text if key_elem is not None else "unknown"
        output["removed_syntax"].append({
            "key": key,
            "description": para_text,
            "related": title_text
        })
    else:
        output["others"].append({
            "key": None,
            "description": para_text,
            "related": title_text
        })


for dirname in sorted(os.listdir(APPENDICES_DIR)):
    dirpath = os.path.join(APPENDICES_DIR, dirname)
    if not os.path.isdir(dirpath):
        continue

    output_breaking_changes_data = {
        "reserved_keywords": [],
        "removed_functions": [],
        "behavior_changes": [],
        "removed_syntax": [],
        "others": []
    }
    output_deprecated_features_data = {
        "deprecated_function": [],
        "removed_syntax": [],
        "others": []
    }
    for xml_file in ["incompatible.xml", "deprecated.xml"]:
        xml_path = os.path.join(dirpath, xml_file)
        if not os.path.exists(xml_path):
            continue

        try:
            with open(xml_path, "rb") as f:
                xml_bytes = f.read()

            # Clean problematic entities
            xml_str = xml_bytes.decode("utf-8")
            xml_str = xml_str.replace("&true;", "true").replace("&false;", "false")
            xml_cleaned = xml_str.encode("utf-8")

            parser = ET.XMLParser(resolve_entities=False, recover=True)
            root = ET.fromstring(xml_cleaned, parser)

            # Apply the correct classification
            for para in root.findall(".//para"):
                if xml_file == "incompatible.xml":
                    classify_breaking_changes(para, output_breaking_changes_data)
                elif xml_file == "deprecated.xml":
                    classify_deprecated_features(para, output_deprecated_features_data)

        except Exception as e:
            print(f"❌ Error processing {xml_path}: {e}")

    for filename_type, output_data in [
        ("breaking-changes", output_breaking_changes_data),
        ("deprecated-features", output_deprecated_features_data)
    ]:
        version_tag = version_from_dirname(dirname)
        out_filename = f"{version_tag}-{filename_type}.json"
        out_path = os.path.join(BASE_DIR, out_filename)
        try:
            with open(out_path, "w", encoding="utf-8") as f:
                json.dump(output_data, f, indent=2, ensure_ascii=False)
            print(f"✔ Written: {out_filename}")
        except Exception as e:
            print(f"❌ Error writing JSON {out_path}: {e}")
