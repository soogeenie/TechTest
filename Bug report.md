# Bug Report

|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Bug Number 1 ##
### Description
CONTENT-TYPE other than application/JSON should be rejected but TEXT/PLAIN is accepted.

### Expected Behaviour
CONTENT-TYPE other than JSON should be rejected. (Status Code = 415)

### Actual Behaviour
CONTENT-TYPE TEXT is accepted. (Status Code = 200)

### Steps to reproduce
- Create a test data file with .txt extention
- Send the test data file to the HTTP POST request (header set to CONTENT-TYPE = TEXT/PLAIN) to the endpoint (https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase)
- The HTTP POST request is accepted but it is expected to be rejected.

|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Bug Number 2 ##
### Description
For the Assigning Facility within patiend_id, any trailing characters that come after "MC" are not validated.

### Expected Behaviour
The Assigning Facility is always 'MC'.

### Actual Behaviour
Any trailing characters that follow the string 'MC, i.e. 'MC0', are not validated. Instead, it's accepted. 
Further investigation is required how it is stored in the database. If the trailing charaters are truncated and just store 'MC', the spec needs to reflfect that to be precise. (Status Code = 200)

### Steps to reproduce
- Create a test data file with .json extension
- Add the following data to the test data file (Assigning Facility = 'MC0')
{
    "case_id": "a474e3e6-89ad-4bb9-be00-cba347e2a001",
    "patient_id": "1234567^1^ISO^NN123^MC0",
    "patient_name": "Smith^John",
    "dob" : "19700401",
    "tissue_type": "prostate"
}
- Send the test data file to the HTTP POST request to the endpoint (https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase)
- The HTTP POST request is accepted but it should have been rejected.

|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Bug Number 3 ##
### Description
patient_name with a string with 3 components is rejected but it should have been accepted.

### Expected Behaviour
patient_name with a string with 3 components should be accepted. (Status Code = 200)

### Actual Behaviour
patient_name with a string with 3 components is rejected. (Status Code = 422)

### Steps to reproduce
- Create a test data file with .json extension
- Add the following data to the test data file ("patient_name": "Smith^John^Lee")
{
    "case_id": "a474e3e6-89ad-4bb9-be00-cba347e2a001",
    "patient_id": "1234567^1^ISO^NN123^MC",
    "patient_name": "Smith^John^Lee",
    "dob" : "19700401",
    "tissue_type": "prostate"
}
- Send the test data file to the HTTP POST request to the endpoint (https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase)
- The HTTP POST request is rejected but it should have been accepted.

|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Bug Number 4 ##
### Description
dob with an invalid date, i.e. 19700230, is not validated. 

### Expected Behaviour
dob with an invalid date is rejected. (Status Code = 422)

### Actual Behaviour
dob wiht an invalid date is accepted. (Status Code = 200)

### Steps to reproduce
- Create a test data file with .json extension
- Add the following data to the test data file ("dob": "19790230")
{
    "case_id": "a474e3e6-89ad-4bb9-be00-cba347e2a001",
    "patient_id": "1234567^1^ISO^NN123^MC",
    "patient_name": "Smith^John",
    "dob" : "19700230",
    "tissue_type": "prostate"
}
- Send the test data file to the HTTP POST request to the endpoint (https://lucent-trifle-ba3d62.netlify.app/.netlify/functions/checkcase)
- The HTTP POST request is accepted but it should have been rejected.

|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Missing Requirements ##
1. The requirements do not include expected behaviour for missing fields or missing values in the case metadata.
2. The requirements do not include expected behaviour when fields, other than the mandatory fields, are listed in the case metadata. 