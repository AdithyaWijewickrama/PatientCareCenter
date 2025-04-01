# Patient Care Center - Medical Management System  

![Banner](https://via.placeholder.com/1024x300/2a4365/ffffff?text=Patient+Care+Center)  
*A comprehensive JavaFX application for healthcare management with PostgreSQL backend*

---

## 🌟 **Key Features**

### 👥 **Patient Management**
- **Complete Demographics**:
  - Name, DOB, gender, marital status
  - Nationality and language preferences
  - Contact details management
  ![Screenshot (67)](https://github.com/user-attachments/assets/81324166-bd64-4495-bedd-9a6b164c33f3)


- **Search & Filter**:
  - Quick access to patient records
  - Action buttons for editing/viewing

### 💊 **Pharmacy & Prescriptions**
- **Smart Prescription System**:
  - Medicine selection from stock
  - Dosage calculation (frequency, duration)
  - Print/Send prescriptions
  ![Screenshot (64)](https://github.com/user-attachments/assets/32b3d638-6c97-4ae7-8f2f-40681cb465eb)


- **Inventory Management**:
  - Stock level indicators (e.g., "VERY HIGH")
  - Expiry date tracking (e.g., RED means Expired,YELLOW - 3 months to expire)
  ![Screenshot (62)](https://github.com/user-attachments/assets/0e8768fb-5a78-4da5-b335-d3fd7c1b53f3)


### 📄 **Medical Documentation**
- **Certificate Generation**:
  - Medical certificates
  - Fitness certificates
  - Ultrasound reports
  

### 🔐 **Security & Administration**
- **Role-based Access**:
  - Doctor/staff accounts
  - Secure authentication
  ![Login](Screenshot (55).png)

---

## 🖥️ **UI Components**

| Module | Screenshot |
|--------|------------|
| **Dashboard** | ![Dashboard](Screenshot (61).png) |
| **Patient Registration** | ![Registration](Screenshot (67).png) |
| **Prescription** | ![Prescription](Screenshot (66).png) |
| **Settings** | ![Settings](Screenshot (53).png) |

---

## 🛠️ **Technical Architecture**

```mermaid
graph TD
    A[JavaFX UI] --> B[Java Backend]
    B --> C[PostgreSQL Database]
    C --> D[Patient Records]
    C --> E[Medicine Inventory]
    C --> F[User Accounts]
```

**Tech Stack**:
- Frontend: JavaFX 17+
- Backend: Java 17 (Spring Boot)
- Database: PostgreSQL 14
- Build: Maven

---

## 🚀 **Getting Started**

### Prerequisites
- Java JDK 17+
- PostgreSQL 14+
- JavaFX SDK

### Installation
```bash
git clone https://github.com/AdithyaWijewickrama/patient-care-center.git
cd patient-care-center
mvn clean install
```

### Configuration
1. Create PostgreSQL database
2. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/pccdb
   spring.datasource.username=admin
   spring.datasource.password=secure123
   ```

---

## 📜 **License**
MIT License © 2025 Adithya Wijewickrama

---

## ✨ **Why Choose This System?**
- **Complete Solution**: From patient registration to pharmacy management
- **User-Friendly**: Intuitive JavaFX interface
- **Reliable**: PostgreSQL-backed data storage
- **Customizable**: Modular design for clinic-specific needs

**GitHub**: [github.com/your-repo](https://github.com/your-repo)  
**Demo**: [Insert Demo Link]  

---

Let me know if you'd like to add any additional sections like contribution guidelines or API documentation!
