# Contact Management App

**Student:** Sanskar Thakur  
**Language:** Java (JDK 11 or newer)  
**Interface:** Console  
**Storage:** Local text file (`data/contacts.csv`)

## App kya karti ho ?

Yeh chhoti address-book app contacts add, view, search, edit aur delete karti hai. Har contact ke liye naam, 10-digit phone number aur email rakhe jaate hain. Data app band hone ke baad bhi file mein save rehta hai.

## Kaise chalayein?

Project folder ke andar terminal kholkar:

```text
javac -encoding UTF-8 -d out ContactApp.java
java -cp out ContactApp
```

Run karte waqt project folder ko current folder rakhein; isi jagah `data` folder banega.

## Features

- Name ya phone number se search
- Phone number ke liye exactly 10 digits ki validation
- Email format ki basic validation
- Contact edit karte waqt Enter dabakar kisi field ko same rakh sakte hain
- Har change local file mein save hota hai

## Concepts

Classes, objects, `ArrayList`, loops, methods, input validation, regular expressions aur file handling.

## Apna demo kaise banayein?

App run karke kuch sample contacts add karein, search/edit/delete dikhayein, phir app band karke dobara kholkar persistence check karein. Apne screenshots README mein add kar sakte hain.
