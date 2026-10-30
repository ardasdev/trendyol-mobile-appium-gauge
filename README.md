# Trendyol Mobile Test Automation — Appium + Gauge

Android UI test automation for the Trendyol mobile app, written during my QA internship (June–July 2026).

## Tech stack

Java 17 · Appium (UiAutomator2) · Gauge · JUnit 5 assertions · Maven

## What is tested

| Spec | Scenarios |
| --- | --- |
| `uygulama-acma.spec` | Launching the app |
| `kullanici-girisi.spec` | Login, logout |
| `listeleme-filtreleme.spec` | Browsing categories, sorting by price, price range filter |
| `favoriler-magaza.spec` | Add / remove favourites, follow / unfollow a store |

## Design

- **Element repository:** Android locators (resource IDs, XPath) live in `src/test/resources/elements.json`.
- **Device configuration** (Appium server URL, platform, device) lives in `config.properties` and is read by `ConfigReader`.
- **Concepts** group reusable flows: opening the app, logging in only if needed, searching, adding to cart.
- **Credentials are never committed.** They are read from the `TRENDYOL_EMAIL` and `TRENDYOL_SIFRE` environment variables.

## How to run

Requirements: Android emulator or device with the Trendyol app installed, Appium server running on `http://127.0.0.1:4723`, and [Gauge](https://gauge.org/) with the Java plugin.

```powershell
# Windows PowerShell
$env:TRENDYOL_EMAIL = "your-test-account@example.com"
$env:TRENDYOL_SIFRE = "your-password"
gauge run specs
```

## Known limitations

- The tests depend on the live Trendyol app; UI changes in new app versions can break locators.
