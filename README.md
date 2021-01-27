# webbootstrapper

# Wallet API GUIDE!

Welcome to the new blockchain technology of TAKAMAKA. 
The purpose of this guide is to offer an easy instruction guide to start using the API of this thrilled technology and customize the front end adopting it to the technology or programing language that most suits your needs. 


# AWARENESS

StackEdit stores your files in your browser, which means all your files are automatically saved locally and are accessible **offline!**

# THREE IMPORTANT  FUNCTIONS
## The calls of the API of these respective functions  will be presented as follows. 

## 1. Get Address 

## 2. Pay

## 3. Blob file


# GET ADDRESS 
To start using Takamaka technology. you need to first create a wallet. Every wallet has one or more addresses. When the wallet is created it is also created its default address.
If we think of the wallet as an entity it has the following features:

 1. Name of wallet which is decided when the wallet is created.
 2. Password Is a password that is created during the wallet creation and the only constraint that mus respect is that must have at least 8 characters.
 3. The Cypher used for the walled, example Ed25519BC.
*Be careful * the password is personal and should not be shown to public. That is the reason that the system once created the wallet does encrypt the password so it is not possible to unlock the wallet.
4. Address of wallet created has 44 characters and it ends with a point. 

We are presenting validated code with POSTMAN for a common and easy way to make things go smooth for your implementation.
Wallet first creation. 
## Plain Password example
In the first step creation of the wallet you can spot that the password is the real password used when wallet is created.  
The uuid for php is in the following link: https://www.php.net/manual/en/function.uniqid.php

> Wallet Name: 	**Test-Wallet-Name**
> Password: 		**Password**


```json
{
"itb":  null,
"rt":  "GET_ADDRESS",
"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
"wallet":  {
	"addressNumber":  "0",
	"walletCypher":  "Ed25519BC",
	"walletName":  "Test-Wallet-Name",
	"walletPassword": "Password"
	}
}
```
Response Postman platform.
> Address of wallet:  **gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.**

 ```json
{
	"request":  {
	"rt":  "GET_ADDRESS",
	"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
		"wallet":  {
		"addressNumber":  0,
		"walletCypher":  "Ed25519BC",
		"walletName":  "Test-Wallet-Name",
		"walletPassword": "isEncriptedPasswordWithAES256§fe7d2e707dff67ae08bb7f9547b94ea9"
		}
	},
	"signedResponse":  "GET_ADDRESS",
	"walletAddress": "gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.",
	"walletKey":  0
}
```

## Encrypted Password example
For the same wallet, using the API presented when created, it is strongly recommended to use the encrypted password returned. Try the code below on Postman platform.
```json
{
"itb":  null,
"rt":  "GET_ADDRESS",
"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
"wallet":  {
	"addressNumber":  "0",
	"walletCypher":  "Ed25519BC",
	"walletName":  "Test-Wallet-Name",
	"walletPassword": "isEncriptedPasswordWithAES256§fe7d2e707dff67ae08bb7f9547b94ea9"
	}
}
```
Response returned by Postman platform using the encrypted password. It is the same response as the previous one even using the encrypted password. 
 ```json
{
	"request":  {
	"rt":  "GET_ADDRESS",
	"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
		"wallet":  {
		"addressNumber":  0,
		"walletCypher":  "Ed25519BC",
		"walletName":  "Test-Wallet-Name",
		"walletPassword": "isEncriptedPasswordWithAES256§fe7d2e707dff67ae08bb7f9547b94ea9"
		}
	},
	"signedResponse":  "GET_ADDRESS",
	"walletAddress": "gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.",
	"walletKey":  0
}
```

Nothing changes from the previous response for the simple fact that once the wallet has been created for the first time,  on server side the *.tkm-chain/walletWeb/ folder is created and for security reasons each time these files are used to create an encrypted version of the password when clients ask to login into the wallet.  That helps the system use the encrypted version of the password for security reasons.



# PAY 
Once we have a wallet available with all its features it is time to start exploring the potentials. One of the main features of a wallet address is that of transferring some of the balance towards another address. 
At the beginning we said that one wallet can have ore more addresses available. Each address has its own balance and by using the available tokens you can decide to transfer them towards another address, that can belong to another wallet or to the same wallet.
## Postman example API - PAY
### Send Request
There is a request of 45000000000 green tokens and  333000000000 red tokens to be sent to the "bh1-Tes-WalletOU7MsemfdiCUOlM9_GV-Y7vWS-MgFRxUuWJvbdY." address,  from the Test-Wallet-Name with addressNumber=0 and its password using the function PAY. 
```json
{
"itb":{
"greenValue":  "45000000000",
"message":  "Pay API for test reasons",
"redValue":  "333000000000",
"to":  "bh1-OU7MsemfdiCUOlM9_GV-Y7vWS-MgFRxUuWJvbdY.",
"transactionType":  "PAY"
},
"rt":  "PAY",
"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
	"wallet":{
	"addressNumber":  0,
	"walletCypher":  "Ed25519BC",
	"walletName":  "Test-Wallet-Name",
	"walletPassword": "isEncriptedPasswordWithAES256§fe7d2e707dff67ae08bb7f9547b94ea9"
	}
}
``` 
### Response 
This is the transaction generated.  Let's look closer the response. 

- To address is the address that is going to receive the transaction funds and the message.
 - From address is that sending the amount of tokens red and/or  green. *Important that at least one of the fields greenValue/redValue has to be more than '0' because otherwise the transaction is not created.* That is generated from the API send request by simply indicating the name of the wallet its encrypted password and the addressNumber = 0. Automatically the address corresponding is presented as it is written in the response below.  
  - There is a customized message that is send to the receiver with the transaction.

 
 - 

```json
{
"feeBean":  {
"addr":  "gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.",
"cpu":  0,
"disk":  78133333,
"hexAddr":  "8294ee25c2d7347522fd2c0449bf0129c6027c74c69feeafce52249702e1cc1a",
"memory":  0,
"sith":  "sDBDR5OTbgif8d-Q7IA4x0zkoYRDiHWq7WUO2JifxcY."
},
"request":  {
"itb":  {
"from":  "gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.",
"greenValue":  45000000000,
"message":  "Pay API for test reasons",
"notBefore":  "2021-01-25T17:50:00.266Z[UTC]",
"redValue":  333000000000,
"to":  "bh1-OU7MsemfdiCUOlM9_GV-Y7vWS-MgFRxUuWJvbdY.",
"transactionHash":  "qDDzuISljy1ZGHIuMG9DihVnzCLopy-CYV0aetioIfE.",
"transactionType":  "PAY"
},
"rt":  "PAY",
"uuid":  "ddb3327f-11ff-4a91-8ee2-2a11fc8fc4f9",
	"wallet":  {
	"addressNumber":  0,
	"walletCypher":  "Ed25519BC",
	"walletName":  "Test-Wallet-Name",
	"walletPassword": "isEncriptedPasswordWithAES256§fe7d2e707dff67ae08bb7f9547b94ea9"
	}
},
"signedResponse":  "PAY",
"trxJson":  "{\"publicKey\":\"gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.\",\"signature\":\"q0Nq6KdvcKEKZ2pQEIDMsjv7noKNNNhgTEdWZgVFwZ1z5_AJbUkrea9gz6k2QB0VFHueJ1Zb9w8jvHZnv8zLAQ..\",\"message\":\"{\\\"from\\\":\\\"gpTuJcLXNHUi_SwESb8BKcYCfHTGn-6vzlIklwLhzBo.\\\",\\\"to\\\":\\\"bh1-OU7MsemfdiCUOlM9_GV-Y7vWS-MgFRxUuWJvbdY.\\\",\\\"message\\\":\\\"Pay API for test reasons\\\",\\\"notBefore\\\":1611597000266,\\\"redValue\\\":333000000000,\\\"greenValue\\\":45000000000,\\\"transactionType\\\":\\\"PAY\\\",\\\"transactionHash\\\":\\\"qDDzuISljy1ZGHIuMG9DihVnzCLopy-CYV0aetioIfE.\\\",\\\"epoch\\\":null,\\\"slot\\\":null}\",\"randomSeed\":\"BhZu\",\"walletCypher\":\"Ed25519BC\"}",
"walletKey":  0
}


```
# BLOB FILE TEXT
It is often necessary to store inside the blockchain some important information that can be a determined file. This is possible by simply using the API underneath presented as follows.


```json


```
 

```html
   // code for coloring
```
```js
   // code for coloring
```
```css
   // code for coloring
```
```
```diff
- text in red
+ text in green
! text in orange
# text in gray
@@ te
```