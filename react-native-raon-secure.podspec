require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-raon-secure"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = "https://github.com/trabricks-react/react-native-raon-secure"
  s.license      = "MIT"
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/trabricks-react/react-native-raon-secure.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.vendored_frameworks = 'ios/RSLicenseSDK.framework'
end

