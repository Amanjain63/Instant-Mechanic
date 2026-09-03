# Instant Mechanic - Android Service App

An Android application built for the **Android Development Internship** assignment. The app allows users to browse nearby mechanics, view detailed garage information, filter services, and submit service requests.

---

## 📱 Features & Requirements Met

### 1. Home Screen
- **Mechanic List**: Displays nearby mechanics/garages fetched via REST API.
- **Garage Details**: Name, star rating, distance, location, available services, and real-time Open/Closed status indicators.
- **Search & Filter**: Real-time search by garage name/location/service and filtering by specific service categories.

### 2. Mechanic Details
- Selecting any mechanic opens a dedicated details screen showing:
  - Garage Name & Rating
  - Full Address
  - Available Services list
  - Working Hours
  - Contact Phone Number
  - **Request Service** action button

### 3. Request Service Form
- Form allowing users to book a repair service with:
  - Customer Name
  - Phone Number
  - Vehicle Number (e.g., UP11AB1234)
  - Service Selection (dropdown populated dynamically from the mechanic's offered services)
  - Problem Description
- **Confirmation**: Form validation and a confirmation success dialog on submission summarizing all request details.

### 4. API & Data Handling
- **REST API Integration**: Uses Retrofit with Gson converter.
- **State Management**: Uses Coroutines Flows (`StateFlow`) and `UiState`sealed class (`Loading`, `Success`, `Error`) for robust error handling and loading states.
- **Clean Architecture**: Built following the **MVVM (Model-View-ViewModel)** architectural pattern.

---

## 🛠️ Technical Stack & Libraries

- **Language**: Kotlin 2.1.0
- **UI**: XML Layouts with Material Design 3 components & ViewBinding
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit 2 & Gson
- **Concurrency**: Kotlin Coroutines & Flow
- **Lifecycle**: ViewModel & Lifecycle Runtime KTX
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Compile SDK**: 35

---

## 🚀 Setup & Installation Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/InstantMechanic.git
   ```
2. **Open in Android Studio**:
   - Open Android Studio (Ladybug / Jellyfish or newer).
   - Select **Open** and choose the `InstantMechanic` root folder.
3. **Sync Project with Gradle Files**:
   - Allow Android Studio to download dependencies and sync Gradle.
4. **Run the App**:
   - Connect an Android emulator or physical device (API 26+).
   - Click the **Run** (`▶`) button to build and launch the app.

---

## 📐 Architecture Explanation

- **`data` Layer**:
  - `model`: Data classes (`Mechanic`, etc.) annotated with `@Parcelize` for safe fragment argument passing.
  - `api`: Retrofit service interface (`MechanicApi`).
  - `repository`: `MechanicRepository` handling data fetching.
- **`ui` Layer**:
  - **Home**: `HomeFragment`, `HomeViewModel`, and `MechanicAdapter` for displaying and filtering the list of mechanics.
  - **Details**: `MechanicDetailsFragment` displaying detailed garage info.
  - **Request**: `RequestServiceFragment` managing service booking form validation and submission confirmation.
- **`utils`**:
  - `UiState`: Generic sealed class representing UI states (`Loading`, `Success`, `Error`).

---

## 📄 Assumptions & Additional Features
- **Mock / REST Endpoint**: Configured with robust fallback and network handling via Retrofit.
- **Dynamic Service Dropdown**: Service request form automatically adapts to the specific services offered by the chosen mechanic.
- **Modern Android Standards**: Uses ViewBinding, Jetpack Fragments, and Kotlin Parcelize without deprecated serialization APIs.
