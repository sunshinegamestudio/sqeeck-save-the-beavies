copy "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-unsigned.apk" "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-signed.apk"
jarsigner -verbose -keystore "C:\Users\Vortex\.android\debug.keystore" "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-signed.apk" androiddebugkey
jarsigner -verify -verbose -certs "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-signed.apk"
zipalign -v 4 "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-signed.apk" "G:\Development\GameDev\jMP\PlatformGame\engine\dist\Sqeeck_Save_The_Beavies-release-signed-aligned.apk"
