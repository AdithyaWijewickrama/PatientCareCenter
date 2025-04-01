# Patient Care Center - Medical Management System  

![Pcc](https://github.com/user-attachments/assets/8e4f547e-761e-48e8-9ee7-8f1dbbe75781)

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
  - >![Screenshot (61)](https://github.com/user-attachments/assets/38cf8148-7e5f-4642-afda-867da7f05ef0)


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
  - >![Screenshot (48)](https://github.com/user-attachments/assets/42e0b49f-0b60-4161-8f74-bc0231794b7b)

  - Fitness certificates
  - >![Screenshot (49)](https://github.com/user-attachments/assets/918c8882-1f9b-4ad0-ba52-ff3b93e200ae)

  - Ultrasound reports
  - >![Screenshot (51)](https://github.com/user-attachments/assets/fb61ab21-a674-42ab-9e82-b1752221c2a2)
  

### 🔐 **Security & Administration**
- **Role-based Access**:
  - Doctor/staff accounts
  - Secure authentication
  >![Screenshot (55)](https://github.com/user-attachments/assets/028f6f04-ae4c-4470-a16a-4264e322dc09)
  >![Screenshot (56)](https://github.com/user-attachments/assets/2764e805-edd4-480d-856a-07dae1623637)



---

## 🖥️ **UI Components**

| Module | Screenshot |
|--------|------------|
| **Dashboard** | ![Screenshot (61)](https://github.com/user-attachments/assets/88c920b7-a552-4d10-aed0-7e111499fb2c) |
| **Patient Registration** |  |
| **Prescription** | ![Screenshot (64)](https://github.com/user-attachments/assets/fca8ef42-22c4-445b-8e3f-386cfb289127) |
| **Settings** | ![Screenshot (53)](https://github.com/user-attachments/assets/9a31962b-2f96-49ca-a1af-0f1a0a569d08) |
| **Profile** | ![Screenshot (54)](https://github.com/user-attachments/assets/1bd4edd6-f235-4794-b460-500c1a59650b) |

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

**GitHub**: [github.com/AdithyaWijewickrama](https://github.com/AdithyaWijewickrama/PatientCareCenter)  
**Demo**: [video link will be available soon]  

---
