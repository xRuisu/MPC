# Formulas Documentation

This documentation provides an overview of the formulas used in the Metrics Performance Calculator (MPC).

## Variables & Descriptions

- **`D` (Duration)**: Total time span between a given start and end time, converted to minutes.
- **`V` (Volume)**: Total count of pieces scanned by employees for a group or team.
- **`E` (Employees)**: Total number of employees for a group or team.
- **`A` (Average)**: The expected or goal rate for employees.
- **`R` (Rate)**: The actual average rate achieved by an employee.
- **`S` (Scans)**: Total number of scans by an employee during the given start-end time.
- **`ESR`/`RP` (Employee Success Rate/Rate Percentile)**: The percentage of an employee's rate compared to the average (goal).
- **`ICR` (Individual Contribution Rate)**: Percentage of the total volume scanned by an individual employee.
- **`TC` (Total Contribution)**: The total contribution added between all given employee's

---

## Formulas

### How to Calculate:

#### **1. Duration (`D`)**:

- **Formula**: Start-End: `0000-0430` to formula: `D` = ((`He` - `Hs`) \* 60) - `Ms` + `Me`
- `Hs`: Hour start
- `He`: Hour end
- `Ms`: Minute start
- `Me`: Minute end
- **Example**:
  - Start Time: `Hs` = 0, `Ms` = 15
  - End Time: `He` = 4, `Me` = 30

#### Calculation:

    ```
    D = ((4 - 0) * 60) - 15 + 30
    D = (4 * 60) - 15 + 30
    D = 255 minutes
    ```

---

#### **2. Average (`A`)**:

- **Formula**: `A` = `V` / (`D` \* `E`)
- **Example**:
  - Volume: `V` = 4,303
  - Employees: `E` = 4
  - Duration: `D` = 288

#### Calculation:

    ```
    A = 4303 / (288 * 4)
    A = 4303 / 1152
    A ≈ 3.7
    ```

---

#### **3. Rate (`R`)**:

- **Formula**: `R` = `S` / `D`
- **Example**:
  - Scans: `S` = 1202
  - Duration: `D` = 288

#### Calculation:

    ```
    R = 1363 / 288
    R ≈ 4.7
    ```

---

#### **4. RATE PERCENTILE (`RP`)**:

- **Formula**: `RP` = (`R` / `A`) \* 100
- **Example**:
  - Example Rate 1: `R` = 4.2
  - Example Average 2: `A` = 3.7
    #### Calculation:
    ```
    RP = (4.2 / 3.7) * 100
    RP ≈ 113.51%
    ```

---

#### **5. Individual Contribution Rate (`ICR`)**:

- **Formula**: `ICR` = (`S` / `V`) \* 100
- **Example**:
  - Scans: `S` = 1202
  - Volume: `V` = 4,303

#### Calculation:

    ```
    ICR = (1202 / 4303) * 100
    ICR ≈ 27.93%
    ```

---

#### **6. Total Contribution Rate (`TC`)**:

- **Formula**: `n` = number of employees
- **Example**: `TC` = `Worker 1 ICR` + `Worker 2 ICR` + `Worker 3 ICR` + etc...
  #### Calculation:
  ```
  TC = 31.67% + 27.93% + 25.74% + 14.6% = 99.64%
  Usually returns 100% however, didn't use full decimal value.
  ```
  ### Summation Formula:
