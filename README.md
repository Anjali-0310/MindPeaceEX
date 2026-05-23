<img width="358" height="652" alt="Screenshot 2026-05-23 154318" src="https://github.com/user-attachments/assets/f15a468b-aafe-45c9-b889-0449e41a0ad9" />1. Abstract of the App
MindPeace is an Android-based mental wellness application designed to help users manage stress, anxiety, and emotional imbalance through simple, accessible tools. The app integrates features such as guided meditation, calming soundscapes, mood tracking, gratitude journaling, PTSD support tools, and reminders. It provides a user-friendly interface with an aesthetically pleasing design to create a calming digital environment. The application does not diagnose mental health conditions but offers supportive techniques to promote relaxation, self-awareness, and emotional well-being.

2. Purpose of the App
The primary purpose of MindPeace is to provide users with an easily accessible platform for improving mental health and reducing stress in daily life. In today’s fast-paced world, people often lack time or resources to manage their emotional well-being. This app aims to bridge that gap by offering quick and effective tools such as breathing exercises, meditation sessions, and mood tracking. It is especially useful for students and working individuals who experience anxiety, pressure, or emotional fatigue.

3. Features of the App
•	Guided Meditation with timer and audio playback
•	Sound Mixer (multiple relaxing sounds can be played simultaneously)
•	Mood Journal with edit and delete functionality
•	Gratitude Journal for positive reflection
•	PTSD Toolkit with grounding exercises and AI-based reassurance messages
•	Reminder System with alarm notifications and vibration
•	Breathing Exercise module for stress control

4. Significance / Benefits to Society
MindPeace contributes to society by promoting mental health awareness and providing accessible self-care tools. It helps individuals manage stress, anxiety, and emotional imbalance without requiring immediate professional intervention. The app encourages positive habits such as journaling, mindfulness, and gratitude, which improve overall well-being. It can be especially beneficial for students, professionals, and individuals dealing with emotional challenges, thereby contributing to a healthier and more balanced society.

5. Further Improvements
•	Integration of AI chatbot for real-time emotional support
•	Cloud database for syncing user data across devices
•	Personalized recommendations based on user behaviour
•	Advanced analytics dashboard for mood tracking trends
•	Emergency support feature for critical mental health situations
•	Wearable device integration for real-time stress monitoring


WORKING OF THE APPLICATION – MindPeace
The MindPeace application is designed as a modular Android application that helps users manage stress and improve mental well-being through various interactive tools. The working of the application is explained step-by-step as follows:

1. App Launch Flow
When the user opens the application, the first screen displays the app name along with developer and guide details. The user then navigates to the About screen, which provides a brief introduction to the app. After this, the user proceeds to the Sign-Up/Login screen for authentication. Once logged in successfully, the user is directed to the main dashboard.
<img width="358" height="652" alt="Screenshot 2026-05-23 154318" src="https://github.com/user-attachments/assets/97e01a3a-0d4c-46d7-acb5-1e9c94c4acd3" />
<img width="316" height="557" alt="Screenshot 2026-05-23 154327" src="https://github.com/user-attachments/assets/02930720-5c1b-4076-89dc-6bcb2142154e" />

2. Dashboard (Central Control Panel)
The dashboard acts as the central hub of the application. It contains multiple feature cards such as Guided Meditation, Mood Journal, Gratitude Journal, Breathing Exercise, Sound Mixer, PTSD Toolkit, Reminder System, Mind Calculator, and Camera. Each card navigates to its respective module when clicked.
<img width="278" height="499" alt="Screenshot 2026-05-23 154210" src="https://github.com/user-attachments/assets/4d8bb231-1331-4cba-b810-e5f4889984c0" />

3. Guided Meditation Module
This module allows users to play a meditation audio track along with a countdown timer. Users can play, pause, or stop the session. The timer updates every second, and when it completes, the audio stops automatically, helping users complete a structured meditation session.
<img width="274" height="491" alt="Screenshot 2026-05-23 154200" src="https://github.com/user-attachments/assets/e1a7d0e8-c44a-47d3-9307-968f7315da2c" />

4. Sound Mixer Module
Users can play multiple calming sounds such as rain, ocean, forest, fire, piano, and night ambience simultaneously. Each sound can be toggled individually (tap to play/pause), and a “Stop All” button stops all sounds at once. This allows users to create a personalized relaxing environment.
<img width="452" height="788" alt="Screenshot 2026-05-23 154310" src="https://github.com/user-attachments/assets/f8d18279-dade-4d93-81ac-381c9e976965" />
<img width="316" height="557" alt="Screenshot 2026-05-23 154327" src="https://github.com/user-attachments/assets/6f99f07c-97e6-4d37-9cb1-c8d2f82c5a01" />

5. Mood Journal Module
Users can select their mood (happy, sad, calm) and write their thoughts. The entries are stored in an SQLite database. Users can view, edit, and delete past entries. This helps users track emotional patterns over time.
<img width="284" height="497" alt="Screenshot 2026-05-23 154238" src="https://github.com/user-attachments/assets/f7880a66-1962-4d47-a700-ed0224341063" />

6. Gratitude Journal Module
Users can select predefined templates and record things they are grateful for. Entries are saved in the database and displayed in a list with options to edit or delete. This feature promotes positive thinking and mental well-being.
<img width="368" height="661" alt="Screenshot 2026-05-23 154153" src="https://github.com/user-attachments/assets/15f6e077-d2a2-4f65-8b3d-66a9ba0eca7c" />

7. Breathing Exercise Module
This module guides users through breathing patterns to reduce stress. It helps regulate breathing and calm the mind during anxiety or panic situations.
<img width="278" height="489" alt="Screenshot 2026-05-23 154706" src="https://github.com/user-attachments/assets/19ad5242-6acf-4be7-974f-08503e41e78e" />

8. PTSD Toolkit Module
This module provides support tools for users experiencing stress or trauma.
•	A “Calm Me Now” button plays soothing audio and displays AI-based reassurance messages.
•	Grounding exercises guide users step-by-step to bring attention to the present moment.
•	Users can view PTSD information and symptoms through expandable sections.
•	Quick tools allow navigation to breathing exercises and sound therapy.
<img width="397" height="721" alt="Screenshot 2026-05-23 154247" src="https://github.com/user-attachments/assets/a7bb8b83-a89d-4437-89d9-a433b4521e27" />

9. Reminder System
Users can set reminders with a specific time and optional repeat mode. The app uses AlarmManager to trigger notifications with sound and vibration. All reminders are stored locally and displayed in a list. Users can delete reminders using swipe gestures.
<img width="369" height="669" alt="Screenshot 2026-05-23 154257" src="https://github.com/user-attachments/assets/3a6cf6c0-e74c-461a-95c8-d89f4c087c01" />

10. Mind Calculator Module
This module allows users to input two values representing stress factors and apply arithmetic operations (add, subtract, multiply, divide). The result is interpreted as a “mental load score” and categorized as low, moderate, or high stress. This provides a conceptual way to understand emotional load.
<img width="361" height="659" alt="Screenshot 2026-05-23 154120" src="https://github.com/user-attachments/assets/a14898ff-e8ee-4688-9efb-dfb496b387d8" />

11. Camera Module (Capture Calm)
Users can capture photos of calming environments using the device camera. The captured image is displayed on the screen, encouraging users to focus on positive and peaceful moments.
<img width="273" height="486" alt="Screenshot 2026-05-23 154127" src="https://github.com/user-attachments/assets/8ce70dfa-3e46-4825-b1bd-4198ece2cebc" />

12. Data Management
The application uses SQLite database to store journal and gratitude entries. SharedPreferences are used for storing reminders. This ensures data persistence even after the app is closed.

13. Overall Working Principle
The application integrates multiple mental wellness tools into a single platform. Each module works independently but contributes to the overall goal of reducing stress and improving emotional balance. The app focuses on self-help techniques rather than medical diagnosis, making it accessible and easy to use for daily mental health support.
________________________________________

