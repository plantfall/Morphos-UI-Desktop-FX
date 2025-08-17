# Tutorial

This tutorial will guide you through setting up your desktop application with everything ready to go.

## Getting the Application

Here you going to see the ways you can use to initialize your own project.

There are two ways to initialize your own project:

1. Download the app archive  
   [Click here to download `my-coesion-app.rar`](https://github.com/eliezerBrasilian/Coesion-Effect/releases/download/v3/my_coesion_effect_dist_v3.zip)

2. Or, on Windows, open PowerShell and run:

   ```powershell
   Invoke-WebRequest -Uri "https://github.com/eliezerBrasilian/Coesion-JavaFX/releases/download/v2/app_base.rar" -OutFile "my-coesion-app.rar"
   ```

Then extract the archive and open the folder in your preferred IDE like VS Code. You're now ready to dive in!

[Coesion Effect comunnity on Telegram](https://t.me/coesion_effect)

## Building your App

```bash
mvn clean package
```

Or you can use Cryxie Cli directly

```bash
cryxie build --mvn
```

## Distributing Your App

After you have builded it you can distribute easily on two ways

1. Enter manually into scripts folder and run create-installer.bat

```bash
cd scripts
```

```bash
.\create-installer.bat
```

2. Or you can do with cryxie-cli

```bash
cryxie dist-javafx-app --windows
```

After that, your app will be generated in the `dist` folder:

- The `.exe` will be inside `dist/MyApp`
- The `.msi` installer will be inside `dist/`

## Testing Your App Programatically

In order to test your app, you have two options.

1. Run the folloywing command with cryxie-cli

```bash
cryxie test-javafx-app --windows
```

2. Or you can enter manually into scripts folder and run test-app.bat

```bash
cd scripts
```

```bash
.\test-app.bat
```

## Customizing Your App

To update metadata like description, icon, version, and vendor name, edit the `jpackage` section inside `scripts/create-installer.bat.`

## Contribute

Want to contribute?
Feel free to open a PR and become part of the team behind this open-source project!

## Requirements

Make sure you have the following installed:

- WiX Toolset (required to generate MSI installers)
  [Download WixToolset 3.14.1(wix314.exe)](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm)

Then install the app and procced with installation steps. After that you have to set the variable path.
![wix_tollset_path](https://github.com/user-attachments/assets/d92cc6ec-fdd9-4eac-bb82-1c878fa66937)

- Cryxie-cli (if you want to run native commands easily)
  https://cryxie.com/documentation/installation-guide
