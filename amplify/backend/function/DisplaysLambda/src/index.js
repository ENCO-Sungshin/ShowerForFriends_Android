/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_DISPLAYSTABLE_ARN
	STORAGE_DISPLAYSTABLE_NAME
	STORAGE_DISPLAYSTABLE_STREAMARN
	STORAGE_USERSTABLE_ARN
	STORAGE_USERSTABLE_NAME
	STORAGE_USERSTABLE_STREAMARN
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
