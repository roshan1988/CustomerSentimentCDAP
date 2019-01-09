# CustomerSentimentCDAP

## Steps to run the system

1. Build and Deploy the app in CDAP

2. Start service **UserProfileService**

3. Use CDAP Client to register users to the system<br/>
    `cdap cli call service CustomerSentimentApp.UserProfileService POST user body '{"id":"Roshan","firstName":"Roshan","lastName":"Alexander","facebookId":"roshana"}'`<br/>
    `cdap cli call service CustomerSentimentApp.UserProfileService POST user body '{"id":"Rajan","firstName":"Rajan","lastName":"Madhavan","facebookId":"rajanm"}'`

4. Add events to the stream **purchaseStream**<br/>
`Roshan bought Ninja`<br/>
`Rajan bought Audi`<br/>

5. Start flow **PurchaseFlow**	

6. Add events to the stream **facebookPostsStream**<br/>
`roshana:Really happy with the Ninja buy. Astounding performance`<br/>
`rajanm:Problems after buying Audi. Hoping for things to get fixed by service centre`

7. Start flow **SentimentFlow**	

8. Start service **SentimentService**

9. Get the centralized view of a user, purchase, and the sentiments for the users after purchase<br/>
    `cdap cli call service CustomerSentimentApp.SentimentService GET sentiment/Roshan`<br/>
    `cdap cli call service CustomerSentimentApp.SentimentService GET sentiment/Rajan`


