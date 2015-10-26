# exercise-android

Hi Santigo and people from Solstice.

I dedicated some hours to the code challenge Yesterday. You can follow my work on https://github.com/mciruzzi/exercise-android. I made the repository private, so you can either provide me an account to add as a contributor or I could change it to be public.

Regarding what I've been doing... I made the sample app on Android-Studio using gradle as the build system. I started thinking about making it on plain android AsyncTask and HttpURLConnection but then I switched to Volley framework ( I had to read about it first a bit since I had not used it before). I also used Genymotion emulator to test and debug the app.

I mainly programmed two Activities containing 1 fragment each that queries against the provided REST endpoint. First one calls the REST api an populates an build a contact adapter to populate a listView. Then, if any contact item is selected, I start a second activity that queries for contact details and populates them is second view. I also added a loading effect for the contact-list Activity.

I had some issues regarding image-loading with volley framework that made my activity not re-use the existing list element view. I understand this is not a good practice but I wanted to avoid it without much effort and time now. It may be a bug that I can correct with some more time.

I hope this fulfills your expectations. Let me know if so. Otherwise, you can contact me if something is missing, or if it would be better to do it another way.

P.S. Some repository commits are made with my brother Github account by mistake. You can trust me I was the one performing that ones.

Regards
Martin
