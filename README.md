**Mobile Application Assignment Testing Report**
**By Artem Kapustin**



**Introduction:**
Briefly about the application.
The application that was built is a type of computer configurator, with the ability to manually select parts for configuration, as well as fully automatic configuration of the computer, using budget parameters and purposes for using the computer.
The Firebase database was used for storing the data like details, categories and configuration information.
Firebase Storage was used for storing the images for the Application.
Firebase Authentication was used for Registration and Login of users.
In most Java Classes data binding was used for connecting XML elements and Java function together.











**First Tests of the Application:**
Prior to testing:
For the very first tests of the application, one user tested the operation of the Manual Configurator, with a menu of configurators, as well as with the function of adding computer parts to the configuration
![image](https://github.com/user-attachments/assets/4154b88e-6dc1-4818-a774-4cbd8d3466de)

   


** 1st User Feedback:**
After using the catalog and adding products, I saw that the parts were being added to the manual configurator. However, if I added a new part from the same category, they did not change, but the first part remained. 
The second drawback that I noticed is the lack of a configuration reset if you want to change all the parts at once. It would be convenient to add a Reset button. Also, in my opinion, it would be useful to save configurations so that they can be opened later, since collecting the same configuration anew each time takes a lot of time.
Based on my experience using the manual configurator and catalog, I liked the user interface, it is quite convenient and looks nice. However, as I said above, I would add reset and save buttons, as well as the ability to open saved configurations.
**2nd User Feedback:**
I was pleased to use this application. I liked the design solutions, font and colors of the application itself. I would like to add that the Manual Configurator page does not have any way to interact with it, I could not interact in any way with the details that I added, for example I would like to remove products from the configuration.
At first glance the app looks great, however it lacks more interaction with the configurations and it would be useful to add the ability to view and save configurations


**Discussion:**
After studying the feedback from these 2 users. The following errors and shortcomings were identified:
Lack of reset button for manual configurator.
Lack of ability to save the configuration for the user for later viewing.
Lack of ability to view saved configurations.
There is a bug in the function of adding multiple details for one category.
Reset functions for the manual configurator have been added along with a button:
![image](https://github.com/user-attachments/assets/2c47b7ec-cd97-47bf-af1e-006a977a623f)
  
Functions for saving and viewing of saved configuration were added:
 ![image](https://github.com/user-attachments/assets/4ec825a1-a4c7-45c7-9fea-d02c179508f7)

The function of adding a new item for the same category was fixed:
 ![image](https://github.com/user-attachments/assets/954ac0f9-daa5-4753-bc5f-fe6da85445dd)

App screenshots (post-user testing):
![image](https://github.com/user-attachments/assets/15806aa0-2bd0-45c8-ad78-18bd335aee7c)
   

**
Second Test of the Application:**
Prior to testing:
Dashboard catalog pages.
First version of design of dashboard catalog page
![image](https://github.com/user-attachments/assets/b4bd4fc8-cc1d-4ed6-bf3b-ed13409d0ed4)
 
**
1st User feedback:**
I do not like having very small category buttons. I also do not like that the products themselves and their pictures did not seem large enough. I had to look closely to read the small print. The font size is small itself, so it is hard to read.
 ** 
2nd User Feedback:**
The overall design of the product page looks good, but I think the size of the text is quite small, so it is hard to see that. 


**Discussion:**
After analyzing both user reviews, it's evident that there are two primary concerns: the size of category buttons and the font size. Addressing these concerns is crucial to improving the user experience of the product page. Increasing the size of category buttons, enlarging product images, and adjusting the font size will likely solve the issues raised by users and improve the usability and readability of the page. 
The changes were made after the testing:
Category buttons were replaced and move to the bottom of the page.
Product containers were changed and pictures were enlarged,
The font size was also enlarged.
App screenshots (post-user testing): 
![image](https://github.com/user-attachments/assets/52b44e56-77b4-4ccb-8b27-3a0707fdb058)
 





**Third Test of the Application:**
Prior to testing: 
Dashboard catalog pages.
Second version of design of dashboard catalog page
![image](https://github.com/user-attachments/assets/5d7b36cc-f1a3-4da2-98f6-8ebf17de15ad)
 
**1st User Feedback:**
I have tested the Product Catalog page and overall look of this page is good, but with some missing features. On my opinion the categories name for choosing the categories are hard to read because of similar font weight. There is no search by detail names, so it is hard to find the detail I need. There is also lack of information on the detail containers, I think it can be useful to add extra information about the detail.
**2nd User Feedback:**
I was using the latest version of the application at the time of testing. I checked the product catalog page. I did not find the ability to search by product names.
Also from the design side, the text blends together quite a lot due to the lack of variety. Also, I did not see the pages of the categories themselves, categories cannot be selected and the details of each category cannot be viewed, however, the menu for selecting a category exists.

**Discussion:**
After testing the catalog dashboard page, final changes were made.
The ability to search for products by name has been added,
Artwork for each category has been added for a better look and proper design.
Also, additional product information has been added to the detail containers.
Category pages have also been added, now each category has its own page with its products
App screenshots (post-user testing): 
![image](https://github.com/user-attachments/assets/18bae1a5-0e36-47f3-9d92-f6b7fd60dceb)
     



**
Fourth Testing of the Application:**
Prior to testing: 
Automatic configurator pages
![image](https://github.com/user-attachments/assets/518c3bbf-09e1-4ba2-bbde-287a7e82030b)
  

**1st User Feedback:**
After testing automatic configurator, I like the idea of it and design of the page. I was able to choose one of three budgets and purposes for my PC and get a configuration. I like configurations, it gives me a good Computers with a good total price. However, I was not able to save configuration. I think it will be useful to add an opportunity to save configurations.

**Discussion:**
After studying the user's feedback, the following problem was identified:
lack of ability to save configurations.
Changes made to the application after these tests:
After receiving the configuration, the user has the option to save the configuration.
After receiving the configuration in automatic mode, the configuration itself is transferred to the Manual configurator and the user is able to edit the configurator
App screenshots (post-user testing): 
![image](https://github.com/user-attachments/assets/bcb69e16-be8b-41a5-be04-c3632c8ae602)
   

