# product-shop-website
**product-shop-website** projects is developed by Java using **Spring Boot** and **Thymeleaf** frameworks to provide an interactive website that allow users to fill the form to buy products and check for client detail which is stored in database.

## Overview
The project utilize **Spring Boot** framework along with **Thymeleaf** to provide *Springboot website* features multiple web page for storing data in frontend side by fill `buy-product-form` and looking for client detail by specific `Client.clientId`

## Every web page.
### Homepage.

Utilize path: `localhost:8080/home-webpage`.

![](./public/img/home-page.png)

### Client buy product form.

Utilize path: `localhost:8080/buy-product-form`.

![](./public/img/buy-form-1.png)
![](./public/img/buy-form-2.png)

### Display receipt result.
Utilize path: `localhost:8080/buy-product-form/save` with `post` method.

1. If data has been updated correctly it will access this web page:
![](./public/img/receipt-display.png)

2. If data haven't been updated or conflict occur:
![](./public/img/receipt-display-failed.png)

### Get client detail form.
Utilize path: `localhost:8080/get-client-id-detail-form` with `post` method
![](./public/img/get-client-id-detail-form.png)

### Display client detail.
Utilize path: `localhost:8080/get-client-id-detail-form/search` with `get` method.

1. If data has been retrived:
![](./public/img/display-client-detail.png)

2. If data haven't been retrived or conflict occur:
![](./public/img/receipt-display-failed.png)

## Diagram
### Buy product form
![](./public/img/client-buy-product-form-overview.png)

### Get client detail form
![](./public/img/get_client_details_overview.png)

## Usage 
Before executing projects, a few initial setup is necessary.

### Custom Setup 
1. Users can custom setup `products_data.json` for products data. 

    Bear in mind, data structure must be:
    ```JSON
    {
        "SIM_STANDARD": {
            "name": "SIM_STANDARD",
            "price": 450
        },
        "SIM_PREMIUM": {
            "name": "SIM_PREMIUM",
            "price": 700
        },
        "phone_case": {
            "name": "phone_case",
            "price": 300
        }
    }
    ```

2. User can set `status` in `ClientForm.java`.
    ```Java
    public void setStatusRefernceFromPayment() {
        this.status = this.totalPayment >= 2000 ? "Premium": 
                      this.totalPayment >= 1000 ? "VIP":
                      "Standard";
    }
    ```

### Executing Project
If changed has made. `build` command should be executed first. 
```Bash
.\gradlew build
```

Execute project with `main-website` parent directory.
```Bash
.\gradlew bootRun
```