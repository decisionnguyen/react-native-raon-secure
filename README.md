
# react-native-raon-secure

## Getting started

`$ npm install react-native-raon-secure --save`

### Mostly automatic installation

`$ react-native link react-native-raon-secure`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-raon-secure` and add `RNRaonSecure.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRaonSecure.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNRaonSecurePackage;` to the imports at the top of the file
  - Add `new RNRaonSecurePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-raon-secure'
  	project(':react-native-raon-secure').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-raon-secure/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-raon-secure')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNRaonSecure.sln` in `node_modules/react-native-raon-secure/windows/RNRaonSecure.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Raon.Secure.RNRaonSecure;` to the usings at the top of the file
  - Add `new RNRaonSecurePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNRaonSecure from 'react-native-raon-secure';

// TODO: What to do with the module?
RNRaonSecure;
```
  