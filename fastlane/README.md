# Fastlane Configuration for DiceOverflow

This directory contains the Fastlane configuration for automating the build, test, and deployment process of the DiceOverflow Android application.

## Files Overview

- **Fastfile**: Contains all the lanes for different automation tasks
- **Appfile**: Contains app-specific configuration like package name and identifiers
- **Gemfile**: Defines Ruby dependencies for Fastlane

## Available Lanes

### Basic Operations
- `fastlane test` - Runs all unit tests
- `fastlane build_debug` - Builds a debug APK
- `fastlane build_release` - Builds a release APK
- `fastlane lint` - Runs Android lint checks
- `fastlane clean` - Cleans the project

### CI/CD Pipeline
- `fastlane ci` - Complete CI pipeline (clean, lint, test, build)
- `fastlane deploy` - Deploys to Google Play Store (requires setup)

### Development Tools
- `fastlane bump_version` - Increments version code
- `fastlane screenshots` - Takes automated screenshots
- `fastlane coverage` - Generates code coverage report

## Setup

1. Install Ruby and Bundler
2. Install dependencies: `bundle install`
3. Run setup: `fastlane setup` (if available)

## Usage

Run any lane from the project root:
```bash
fastlane [lane_name]
```

Example:
```bash
fastlane build_debug
```

## Google Play Store Setup

To use the `deploy` lane, you'll need to:
1. Create a service account in Google Play Console
2. Download the JSON key file
3. Update the Appfile with the path to the JSON key
4. Uncomment and configure the `json_key_file` line in Appfile

## Customization

You can add new lanes to the Fastfile or modify existing ones to suit your project needs. For more information, visit [Fastlane documentation](https://docs.fastlane.tools/).
