CRUD Web App using AWS Serverless
=================================

This project demonstrates how to create a fully working HTTPS website using AWS serverless services. There are couple of configurations that needed to be done on each service. 

CRUD - means Create, Read, Update, Delete

- [Route53](https://console.aws.amazon.com/route53) (register domain name, create A record to point to CloudFront)
- [CloudFront](https://console.aws.amazon.com/cloudfront) (points to API Gateway and S3 thru multiple origins (1 for API Gateway, 1 for S3), also you can make website HTTPS enabled with free SSL cert).
- [API Gateway](https://console.aws.amazon.com/apigateway) (connects to Lambda, create Methods for POST, GET, DELETE)
- [Lambda](https://console.aws.amazon.com/lambda) (Java8, API endpoints for Create, Read, Delete. Check Java Code on how to interact with DynamoDB)
- [S3](https://s3.console.aws.amazon.com/) (store HTML file) 
- [DynamoDB](https://console.aws.amazon.com/dynamodb) (store data)

![image](https://user-images.githubusercontent.com/4587445/123011931-ec4e4c00-d3f3-11eb-9df3-8970de879024.png)

AWS Diagram created using https://app.cloudcraft.co/

Sample Page
-----------

![image](https://user-images.githubusercontent.com/4587445/123012513-f3298e80-d3f4-11eb-8de6-4dd2851cbd4e.png)

Route53
-------

![image](https://user-images.githubusercontent.com/4587445/123012756-6d5a1300-d3f5-11eb-88dd-c4738871e079.png)

CloudFront
----------

![image](https://user-images.githubusercontent.com/4587445/123012940-caee5f80-d3f5-11eb-8b35-e73e8ce27796.png)

API Gateway
-----------

![image](https://user-images.githubusercontent.com/4587445/123014465-e5760800-d3f8-11eb-9d95-5033bedfdfc2.png)

Lambda (Java8)
--------------

![image](https://user-images.githubusercontent.com/4587445/123014570-1ce4b480-d3f9-11eb-8a13-ca2dd884625b.png)
