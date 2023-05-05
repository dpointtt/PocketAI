# PocketAI
Android application for convenient use of Chat-GPT right in your phone. The application uses the OpenAi API to communicate with artificial intelligence.

In this project, the text-davinci-03 model was used for testing.

In any case, this application is still raw and far from ideal, but it implements all the interesting things that I planned, namely, the initialization of Activity components using the @Tag and @OnClick annotations. This was done to avoid constant repetition of code during normal component initialization, by type - Buttob button = findById<>(). (As a Spring Boot developer, I like this approach better, as I'm used to simplifying everything with annotations)
