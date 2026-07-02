# XFlix Streaming Platform — QA Automation Suite

## 📖 Overview
End-to-end automated test suite for **XFlix**, a video streaming platform similar to Netflix/YouTube. Built as part of the Crio.do QA Automation learning track using Selenium WebDriver, Java, and TestNG.

Validates core streaming platform workflows including video search, genre filtering, content rating validation, and video playback interactions.

---

## 🚀 Key Testing Highlights
- **Page Object Model (POM):** Clean POM architecture separating test logic from UI interactions
- **Data-Driven Testing:** Parameterized test scenarios using TestNG `@DataProvider`
- **Cross-functional Coverage:** Search, filter, sort, and playback workflows — positive and negative
- **Regression Suite:** Modular regression suite covering critical user journeys end-to-end
- **Automated Reporting:** HTML test reports using TestNG listeners

---

## 🛠️ Tech Stack
| Technology | Purpose |
|---|---|
| Java | Core language |
| Selenium WebDriver | Browser automation |
| TestNG | Test runner & assertions |
| Maven | Build & dependency management |
| Page Object Model | Framework architecture |

---

## 📂 Project Structure
```
crio-xflix-streaming-qa/
├── src/
│   ├── main/java/pages/
│   │   ├── HomePage.java
│   │   ├── VideoPage.java
│   │   └── SearchPage.java
│   └── test/java/tests/
│       ├── SearchTest.java
│       ├── FilterTest.java
│       └── PlaybackTest.java
├── test-output/
├── pom.xml
└── README.md
```

---

## ✅ Test Coverage
| Feature | Test Type | Status |
|---|---|---|
| Video Search by Title | Functional | ✅ |
| Genre Filter | Functional | ✅ |
| Content Rating Filter | Functional | ✅ |
| Sort by Upload Date | Functional | ✅ |
| Sort by View Count | Functional | ✅ |
| Video Playback | Functional | ✅ |
| Invalid Search | Negative | ✅ |
| Regression Suite | Regression | ✅ |

---

## ▶️ How to Run
```bash
git clone https://github.com/ritvikz/crio-xflix-streaming-qa.git
cd crio-xflix-streaming-qa
mvn test
```
View reports: Open `test-output/index.html`

---

## 👤 Author
**Ritvik Singh Chouhan** — Senior QA Automation Engineer | SDET-II
🔗 [GitHub](https://github.com/ritvikz) | [Portfolio](https://www.crio.do/learn/portfolio/ritvikchouhan77/) | [LinkedIn](https://www.linkedin.com/in/ritvik-singh-chouhan-8a2a6815a/)
