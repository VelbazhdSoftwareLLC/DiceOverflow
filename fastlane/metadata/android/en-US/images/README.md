# Screenshots Directory Structure

This directory contains the app screenshots for different device sizes used in the Google Play Store listing.

## Directory Structure

- **phone/** - Screenshots for phones (320-640px width, 360-640px height)
- **sevenInch/** - Screenshots for 7-inch tablets (600-960px width, 480-800px height)  
- **tenInch/** - Screenshots for 10-inch tablets (720-1280px width, 600-1200px height)

## File Naming Convention

Screenshots should be named sequentially:
- `phone/screenshots/1.png`
- `phone/screenshots/2.png`
- `phone/screenshots/3.png`
- etc.

## Screenshot Requirements

- Format: PNG or JPEG
- Maximum file size: 8MB per screenshot
- Maximum number: 8 screenshots per device type

## Generating Screenshots

Use the Fastlane screenshots lane to automatically generate screenshots:
```bash
fastlane screenshots
```

Or manually place screenshots in the appropriate directories before running:
```bash
fastlane deploy
```
