## E-CommerceBackendDemo

<h2>SPRING BOOT REST APPLICATION</h2>

<h3>Introduction</h3>
E-CommerceBackendDemo is an e-commerce framework written in Java and leveraging the Spring framework. 
It's targeting the development commerce-driven websites.

<h5>Main reason for developing this project is learning the Spring Boot framework.</5>

<h3>The project is still in the early stages of development so many features are not finished and/or will be changed</h3>

<h3>FEATURES</h3>

User authentication and authorization are made with JWT token which is passed to a cookie on the website. 
The plan is to add an implementation of OAuth 2.0 authentication.

Categories of products are implemented in a way that every category can have unlimited subcategories. When you want to find all products by categories
it will also search all subcategories and return those too. 

Products can have different variation options (for example - shoes can be in different sizes and colors), so it's important to track the number of products in stock separately
in every variation combination. 

There is a shopping cart in which you can send product items and choose the number of items. After you want to place an order shopping cart
items are deleted and transferred to shop order and order line entities. After an order is completed product item quantity should decrease(still developing these features)

Every user also can have many addresses (only one default one) and an address can have many users (same household). 
An address is used when a user is ordering products.

The idea is to also have different payment methods and payment types (still in early implementation)

Tests haven't been implemented yet.

<h3>Current database design</h3>

![ScreenShot](https://user-images.githubusercontent.com/48119103/191621110-260f88a6-d637-4b60-8be3-a37694bbb526.png)

