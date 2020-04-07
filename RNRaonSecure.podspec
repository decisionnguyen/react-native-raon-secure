require 'json'

Pod::Spec.new do |s|
  package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

  s.name          = "RNRaonSecure"
  s.version       = package['version']
  s.summary       = "Aaa"
  s.authors       = { "Suhan Moon" => "leader@trabricks.io" }
  s.homepage      = "https://github.com/trabricks-react/react-native-raon-secure#readme"
  s.license       = "MIT"
  s.platforms     = { :ios => "9.0", :tvos => "9.0" }
  s.framework     = 'UIKit'
  s.requires_arc  = true
  s.source        = { :git => "https://github.com/trabricks-react/react-native-raon-secure.git", :tag => "master" }
  s.source_files  = "ios/*.{h,m}"

  s.dependency 'React'
  s.vendored_frameworks = 'ios/RSLicenseSDK.framework'
end


