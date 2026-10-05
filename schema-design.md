# Database Schema Design — Smart Clinic Management System

## MySQL relational schema

### doctor
- id: BIGINT, primary key, auto-increment
- name: VARCHAR(100), required
- specialty: VARCHAR(50), required
- email: VARCHAR(255), unique, required
- password: VARCHAR(255), required
- phone: VARCHAR(10), required

### doctor_available_times
- doctor_id: BIGINT, foreign key to doctor.id
- available_times: VARCHAR(50)

### patient
- id: BIGINT, primary key, auto-increment
- name: VARCHAR(100)
- email: VARCHAR(255), unique
- password: VARCHAR(255)
- phone: VARCHAR(10)

### appointment
- id: BIGINT, primary key, auto-increment
- doctor_id: BIGINT, foreign key to doctor.id
- patient_id: BIGINT, foreign key to patient.id
- appointment_time: DATETIME, required
- status: INT (0 = Scheduled, 1 = Completed)

### medical_record
- id: BIGINT, primary key
- patient_id: BIGINT, foreign key to patient.id
- record_date: DATE
- diagnosis: TEXT
- notes: TEXT

## MongoDB prescription document

A prescription document contains:
- prescriptionId
- patientId
- doctorId
- appointmentId
- issueDate
- medications
- notes

## Relationships

- One Doctor can have many Appointments.
- One Patient can have many Appointments.
- Each Appointment belongs to one Doctor and one Patient.
- A Doctor can have multiple available time slots.
- Prescriptions reference the patient, doctor and appointment identifiers.
