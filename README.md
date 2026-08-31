# EasyHome

`EasyHome` is a simple and lightweight home app for call-only devices.

## Features

- 2 Customizable one-click call buttons
- Prevent switching to other apps
- Hidden options for administrators

## Requirements

- Android 10+
- Set `EasyHome` as the device owner
- `Files and media` and `Phone` permissions
- `All files access` permission (Android 11+)

### Set device owner

To set `EasyHome` as the device owner, run the following ADB command:

```bash
adb shell dpm set-device-owner net.nfmcpwr.EasyHome/.DeviceOwnerReceiver
```

## License

This project is licenced under the [
`MIT License`](https://github.com/nfmcpwr/EasyHome/blob/master/LICENSE)