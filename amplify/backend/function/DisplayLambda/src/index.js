/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_DISPLAYTABLE_ARN
	STORAGE_DISPLAYTABLE_NAME
	STORAGE_DISPLAYTABLE_STREAMARN
	STORAGE_USERTABLE_ARN
	STORAGE_USERTABLE_NAME
	STORAGE_USERTABLE_STREAMARN
Amplify Params - DO NOT EDIT */

exports.handler = async (event) => {
    // TODO implement
    const response = {
        statusCode: 200,
    //  Uncomment below to enable CORS requests
    //  headers: {
    //      "Access-Control-Allow-Origin": "*",
    //      "Access-Control-Allow-Headers": "*"
    //  }, 
        body: JSON.stringify('Hello from Lambda!'),
    };
    return response;
};
