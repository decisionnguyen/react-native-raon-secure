
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import "RSKSW/RSKSWICRProtocol.h"

@interface RNRaonSecure : NSObject <RCTBridgeModule> {
    RSKSWICRProtocol *icrp;
}

@end

