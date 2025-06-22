# Test Plan for RESTful API endpoint for the Case Validation

## Overview
This test plan outlines the strategy for testing a RESTful API endpoint responsible for validating a pathology Case. The endpoint expects a JSON payload and returns appropriate HTTP status codes based on the validity of the case metadata.

## Test Scope
- Test the endpoint with HTTP POST containing JSON with a case (REQ-1)
- Test the validation on madnatory fields (REQ-2)
- Test the Validations for required fields and data formats (REQ-3 ~ REQ-7)
- Test appropriate status codes for valid and invalid input (REQ-8)

## Assumptions
- The endpoint is available at `https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase`
- It is a RESTful POST endpoint

## Test Data
JSON contains a case, metadata, describing a pathology specimen. 
For instance,
```json
{
  "case_id": "a474e3e6-89ad-4bb9-be00-cba347e2a001",
  "patient_id": "1234567^1^ISO^NN123^MC",
  "patient_name": "Smith^John",
  "dob": "19700401",
  "tissue_type": "prostate"
}
```

## Test Scenarios
### 1. Content-Type JSON Validation (REQ-1)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 1.1 | Valid JSON, correct CONTENT-TYPE `JSON` | 200 OK if body is valid |
| 1.2 | Valid JSON, correct CONTENT-TYPE `JSON` but blank | 400 Invalid Request |
| 1.3 | Invalid, CONTENT-TYPE 'TEXT' | 415 Unsupported Media |

### 2. Required Fields (REQ-2)
#### 2.1 Missing Field
| Test Case | Missing Field | Expected Response |
|-----------|---------------|-------------------|
| 2.1.1 | `case_id` | 422 Unprocessable content |
| 2.1.2 | `patient_id` | 422 Unprocessable content |
| 2.1.3 | `patient_name` | 422 Unprocessable content |
| 2.1.4 | `dob` | 422 Unprocessable content |
| 2.1.5 | `tissue_type` | 422 Unprocessable content |

#### 2.2 Blank Field ("")
| Test Case | Blank Field | Expected Response |
|-----------|---------------|-------------------|
| 2.2.1 | `case_id` | 422 Unprocessable content |
| 2.2.2 | `patient_id` | 422 Unprocessable content |
| 2.2.3 | `patient_name` | 422 Unprocessable content |
| 2.2.4 | `dob` | 422 Unprocessable content |
| 2.2.5 | `tissue_type` | 422 Unprocessable content |

#### 2.3 Unexpected Field
| Test Case | Unexpected Field | Expected Response |
|-----------|---------------|-------------------|
| 2.3 | `patient_title` | 422 Unprocessable content |

### 3. Field / Data Format Validations
#### 3.1 case_id (REQ-3)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 3.1.1 | Valid UUID | 200 if other fields valid |
| 3.1.2 | Invalid UUID with special character(s) | 422 Unprocessable content |
| 3.1.3 | Invalid format UUID | 422 Unprocessable content |

#### 3.2 patient_id (REQ-4)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 3.2.1 | Valid data in all 5 components | 200 if other fields valid |
| 3.2.2 | Missing component (e.g. only 4 parts) | 422 Unprocessable content |
| 3.2.3 | ID Number > 15 digits | 422 Unprocessable content |
| 3.2.4 | Invalid Digit Flag | 422 Unprocessable content |
| 3.2.5 | Invalid Assigning Authority | 422 Unprocessable content |
| 3.2.6 | Invalid Identifier Type Code (wrong pattern) | 422 Unprocessable content |
| 3.2.7 | Wrong Assigning Facility | 422 Unprocessable content |
| 3.2.8 | Missing delimiter '^' | 422 Unprocessable content |

#### 3.3 patient_name (REQ-5)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 3.3.1 | Valid data in 2 components | 200 if other fields valid |
| 3.3.2 | Valid data in 3 components | 200 if other fields valid |
| 3.3.3 | 1 components | 422 Unprocessable content |
| 3.3.4 | 5 components | 422 Unprocessable content |
| 3.3.5 | Missing delimiter '^' | 422 Unprocessable content |

#### 3.4 dob (REQ-6)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 3.4.1 | Valid date format (e.g. YYYYMMDD) | 200 if other fields valid |
| 3.4.2 | Wrong date format (e.g. DDMMYYYY) | 422 Unprocessable content |
| 3.4.3 | Invalid date (e.g. 20240230) | 422 Unprocessable content |

#### 3.5 tissue_type (REQ-7)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 3.5.1 | Valid tissue_type (prostate, breast, etc.) | 200 if other fields valid |
| 3.5.2 | Unacceptable tissue_type | 422 Unprocessable content |
| 3.5.3 | Multiple values of tissue_type | 422 Unprocessable content |

### 4. Valid Input Test (REQ-8)
| Test Case | Input | Expected Response |
|-----------|-------|-------------------|
| 4.1 | Valid Case JSON | 200 OK + { message: "Case valid" } |

## Tools & Frameworks
- API test client: REST-assured (Java)


## Notes
- All error cases must return 422 with appropriate error messages.
- All success cases must return 200 with "Case valid" message body.
- Extend test plan to include schema validation where feasible.


