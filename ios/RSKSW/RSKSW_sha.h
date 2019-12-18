
#ifndef __RSKSW_SHA_H__
#define __RSKSW_SHA_H__

#include "RSKSW_define.h"

//#define RSKSW_SHA_CBLOCK				64
#define	RSKSW_SHA_DIGEST_LENGTH		20
#define	RSKSW_SHA256_DIGEST_LENGTH	32


#ifdef __cplusplus
extern "C" {
#endif
int RSKSW_SHA			(KSW_BYTE *md, KSW_BYTE *data, KSW_WORD len);
int RSKSW_SHA256			(KSW_BYTE *md, KSW_BYTE *data, KSW_WORD len);

#ifdef __cplusplus
}
#endif


#endif
