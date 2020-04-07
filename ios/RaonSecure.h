#import <React/RCTBridgeModule.h>
#import "RSKSW/RSKSWICRProtocol.h"

@interface RaonSecure : NSObject <RCTBridgeModule> {
    RSKSWICRProtocol *icrp;
}

@end
