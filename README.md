# PlayerWidget

Player is made as Floating Widget. To start the player you need to open app and the button "start". If Android version is >= 23 
user have to confirm "Draw over other apps" setting. After granted permission you have to go back to the app and player will appear automaticly. User can move widget on the screen and stick it to the edges. Also he can control the playing process by clicking on the main button or close it whenever he wants by clicking the x-mark.

Project is build in module structure. The main component is PlayerWidgetService, which controls the UI of the player. But the main functionality is in PlayerWidgetManager, that manages AudioPlayerManager and DownloadManager and forwards all the responses to Service. All the components (Widget with its manager, AudioPlayer, Downloader) are separeted and provides one to another by Dagger2 so that dependency injection principle is followed. Also architecture of the project has a multi level structure, so that when parent component stops or destroys - all of its child components will stop also.

The only problem of the project is DownloadManager. I have no experience working with streams so that I haven't done this part of the task. I've read and research much information about HLS protocole and .m3u8 format. I tried to find out a quick solution to extract audiotrack from the stream, but nothing works. I also found out that ExoPlayer works with such stream formates, but it needs more time to understand the low lewel working with streams and complete the task according to TD.
