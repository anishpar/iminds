export const API_MODE = 'LIVE';  // DEV | LIVE

export const stlConfigurations = {
    'regex': {
        'email': /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/,
        'alphaNum': /^[a-zA-Z0-9 ]*$/,
        'alphaNumOnly': /^[a-zA-Z0-9]*$/,
        'entityNumber': /^[a-zA-Z0-9_\/\\-]*$/,
        'gstin': /^\d{2}[A-Z]{5}\d{4}[A-Z]{1}[A-Z\d]{3}$/,
        'alphaNumHTML': '^[a-zA-Z0-9 ]*$',
        'inventoryNum': /^[a-zA-Z0-9.-]*$/,
        'mobileNo': /^[123456789]\d{9}$/,
        'zipCode': /^[0-9]{6}$/,
        'PANcard': /^[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}$/,
        'digitOnly': /^[0-9]*$/,
        'passwordPattern': /^[a-zA-Z0-9]{8,12}$/,
        'queOptions': /^([^:]+(:[^:]+)*)$/,
        'positiveNumberOnly': /^[0-9]{1,4}$/,
        'textGeneral': /^[ \nA-Za-z0-9.:,?_/-]*$/,
        'alphaOnly' : /^[a-zA-Z]*$/,
        'alphaSpaceOnly' : /^[a-zA-Z ]*$/,
        'onlyInteger' : /^[0-9]*$/,
        'alphaNumUnderscore': /^[a-zA-Z0-9_ ]+$/,
        'phoneNumber': /^[0-9]{8,12}$/,
        'mobile': /^[0-9]\d{9}$/,
        'aadhar': /^[0-9]{12}$/,
        'Passport': /^[A-Za-z]{1}[0-9]{7}$/,
        'bill': /^[a-zA-Z0-9]*$/,
        'recipient': /^[a-zA-Z0-9_@\.\/\\-]*$/,
        'className': /^[a-zA-Z.]+$/,
        'mimeType': /^[a-zA-Z0-9.\\-]+\/[a-zA-Z0-9.\\-]+$/
    },

    'maxLength': {
        'name': 50,
      'maxName': 255,
      'maxText': 32000
    },
    'dateFormat': 'dd/MM/yyyy',
    'dateTimeFormat': 'dd/MM/yyyy HH:mm:ss',
    'viewLabelClass': 'col-md-2 col-sm-6 view-label',
    'viewValueClass': 'col-md-2  col-sm-6 view-value',
    'customerMaxDateRangeMonths': 3,
    'enabledPaymentModes': ['CASH', 'CHEQUE' , 'EDC'],
    'enabledPaymentModesForCAF': ['CASH', 'CHEQUE', 'EDC'],
    'invalidExpression': 'Expression is invalid.',
    'expressionBasedAlias': 'EXPRESSION_BASED',
    'DefaultAlias': 'DEFAULT',
    'expressionBased': 'Expression Based',
    // file upload allowed extentions
    'extensionList': ['png', 'docx', 'doc', 'pdf', 'jpeg', 'jpg', 'pptx', '7z', 'zip', 'rar', 'csv', 'txt'],
    // file upload maxsize
    'maxFileSize': 2000000,
    'emptyString': '-',
    'documentDownloadExt' : {
        'png': 'image/png',
        'docx': 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        'doc': 'application/msword',
        'pdf': 'application/pdf',
        'java': 'text/x-java-source,java',
        'js': 'application/javascript',
        'json': 'application/json',
        'jpeg': 'image/jpeg',
        'jpg': 'image/jpeg',
        'pptx': 'application/vnd.openxmlformats-officedocument.presentationml.presentation',
        '7z': 'application/x-7z-compressed',
        'zip': 'application/zip',
        'rar': 'application/x-rar-compressed',
        'csv': 'text/csv',
        'msg' : 'application/vnd.ms-outlook'
    }
};
