<?php

// Use of reserved keyword as class name (breaking change in PHP 8.0)
class match
{
    public function greet()
    {
        echo "Hello from class match!\n";
    }
}

// Use of reserved word 'mixed' in namespace (breaking change)
namespace mixed\tools;

// Old-style constructor (PHP 8.0 breaking change)
class LegacyConstructor
{
    function LegacyConstructor()
    {
        echo "Old-style constructor called.\n";
    }
}

// Removed function: create_function
$lambda = create_function('$a', 'return $a * 2;');
echo $lambda(5) . "\n";

// Removed function: each
$arr = [1, 2, 3];
reset($arr);
while (list($key, $value) = each($arr)) {
    echo "$key => $value\n";
}

// Removed function: read_exif_data
$exif = read_exif_data('example.jpg');
print_r($exif);

// Removed function: image2wbmp
$image = imagecreate(100, 100);
image2wbmp($image, 'image.wbmp');

// Removed function: gmp_random
$rand = gmp_random(5);
echo gmp_strval($rand) . "\n";

// Removed function: imap_headerinfo with old signature
$mbox = imap_open("{mail.example.com:143}", "user", "pass");
$header = imap_headerinfo($mbox, 1, 80); // 'default_host' was removed

// Removed alias: imap_header
$aliasHeader = imap_header($mbox, 1, 80);

// Removed function: mb_ereg_replace with "e" modifier
$text = "The price is 100 dollars";
$text = mb_ereg_replace("(\d+)", "strtoupper('\\1')", $text, "e");
echo $text;

// Removed function: gzgetss
$fp = gzopen("compressedfile.gz", "r");
$content = gzgetss($fp);
gzclose($fp);
echo $content;

// Assertion behavior change (now throws exceptions in PHP 8.0)
assert(false);

// Comment starting with #[ (now parsed as attribute in PHP 8.0)
// #[This is not an attribute, it's just a comment]

// Deprecated functions: enchant
$broker = enchant_broker_init();
enchant_broker_set_dict_path($broker, ENCHANT_MYSPELL, "/usr/share/enchant");
$dict = enchant_broker_request_dict($broker, "en_US");
enchant_dict_add_to_personal($dict, "foobar");
if (enchant_dict_is_in_session($dict, "foobar")) {
    echo "Word found in session.\n";
}
enchant_broker_free($broker);

// Deprecated libxml_disable_entity_loader
libxml_disable_entity_loader(true);

// Deprecated PGSQL aliases
$conn = pg_connect("host=localhost dbname=test user=test password=test");
echo pg_errormessage($conn);
$result = pg_query($conn, "SELECT * FROM test_table");
echo pg_numrows($result);
echo pg_numfields($result);
echo pg_fieldname($result, 0);
echo pg_fieldtype($result, 0);
echo pg_result($result, 0, 0);
