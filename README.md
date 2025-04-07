# PHP Compatibility Analyzer Agent

The **PHP Compatibility Analyzer Agent** is a command-line tool designed to assist developers in identifying and
addressing compatibility issues when migrating PHP projects. It simulates an AI agent that advises on breaking changes,
deprecated features, and other migration concerns that arise during PHP version upgrades.

The agent uses the following four key tools:

- A **synthetic static PHP parser** that simulates code understanding without requiring a full PHP runtime
- A **migration knowledge base**, powered by structured JSON data derived from official PHP documentation
- A **compatibility analyzer** that coordinates analysis for version compatibility (using `BreakingChangesAnalyzer` and
  `DeprecatedFeaturesAnalyzer`)
- A **migration advisor** that brings it all together to generate actionable migration reports

> ⚠️ Currently supports migration analysis for PHP 8.0 only. However, the design allows for easy extension to support
> future PHP versions.

---

## ✨ Features

- ✅ **Static Code Analysis** of PHP source files
- 🔍 **Detection of Breaking Changes** introduced in PHP 8.0
- ⚠️ **Identification of Deprecated Features**
- 💡 **Migration Advice** for upgrading to PHP 8.0
- 🛠️ Modular and extensible architecture to support future versions
- 🚀 Fast, lightweight, and easy to use from the command line

---

## 📦 Installation & Usage

Clone the repository:

```bash
git clone https://github.com/your-username/php-compatibility-analyzer-agent.git
cd php-compatibility-analyzer-agent
```

Build the project using Gradle:

```bash
./gradlew build
```

To analyze a PHP project (with default PHP code):

```bash
java -jar build/libs/php-compatibility-agent-1.0-SNAPSHOT.jar
```

OR

To analyze a PHP project (with custom PHP file attached):

```bash
java -jar build/libs/php-compatibility-agent-1.0-SNAPSHOT.jar -f your-php-file.php
```

Input the following message as instructed to analyze the code against `PHP 8.0` compatibility:
`Check if this PHP code is compatible with PHP 8.0`

