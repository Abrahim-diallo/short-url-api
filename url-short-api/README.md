# short-url-api
## À propos du projet

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th> 
</tr>
  <tr>
      <td>POST</td>
      <td>api//v1/urls/createShortUrl</td>
      <td>Create ShortUrl</td>
  </tr>
  <tr>
      <td>GET</td>
      <td>api/v1/urls/{shortUrl}</td>
      <td>Get originalUrl By ShortUrl</td>
  </tr>
  <tr>
      <td>GET</td>
      <td>api/v1/urls/redirect/{shortUrl}</td>
      <td>Get redirect by ShorUrl</td>
  </tr>
  <tr>
      <td>GET</td>
      <td>api/v1/urls/getByOriginalUrl</td>
      <td>Get All URLs by url Origine</td>
  </tr>
</table>

## Valid Request Body

##### <a id="createShortUrl"> createShortUrl for Url

``` 
    http://localhost:8080/api/v1/urls/createShortUrl    
        {
          "originalUrl": "https://youtu.be/R76S0tfv36w",
          "expiredAt": "2025-01-01T00:00:00Z"
        }

```

##### <a id="originalUrl">  Get original URL by its short URL

```
      http://localhost:8080/api/v1/urls/{shortUrl}    

```

##### <a id="redirect"> Redirect to the original URL

```
     http://localhost:8080/api/v1/urls/redirect/{shortUrl}
```

##### <a id="getByOriginalUrl"> Get all URLs for an original URL

```
    http://localhost:8080/api/v1/urls/getByOriginalUrl?originalUrl=originalUrl
   
```

### 🔨 Run the App short-url-api

<b>Local</b>

<b>1 )</b> Go to the project's home directory :  `cd short-url-api`

<b>2 )</b> Run App short-url-api <b> `mvn spring-boot:run` </b>

<b>3 )</b> For swagger ui <b> `http://localhost:8080/swagger-ui/index.html`</b>


### Screenshots

<details>
<summary>Click here to show the screenshot of project</summary>
    <p> short-url-api Swagger UI</p>
    <img src ="/home/khalid/Images/Challenger-short-url-api/short-url-api/url-short-api/docs/swagger-ui.png" alt="">

<p> create shortUrl Swagger UI</p>
    <img src ="/home/khalid/Images/Challenger-short-url-api/short-url-api/url-short-api/docs/createShortUrl.png" alt="">

<p> Get urlOriginal by short url Swagger UI</p>
    <img src ="/home/khalid/Images/Challenger-short-url-api/short-url-api/url-short-api/docs/getUrlOriginalByShortUrl.png" alt="">

 <p> allUrls Swagger UI</p>
    <img src ="/home/khalid/Images/Challenger-short-url-api/short-url-api/url-short-api/docs/getAllUrlsByOriginalUrl.png" alt="">

 <p> allUrls Swagger UI</p>
    <img src ="/home/khalid/Images/Challenger-short-url-api/short-url-api/url-short-api/docs/allUrls.png" alt="">
</details>
