
#ifndef	__RSKSW_HMAC_H_
#define	__RSKSW_HMAC_H_

#include "RSKSW_define.h"
#include "RSKSW_sha.h"

#define	HMAC_HASHID_MD5					1
#define	HMAC_HASHID_SHA					2

#define	HMAC_MAX_DIGEST_LENGTH			20
#define	HMAC_MAX_CBLOCK					64

#ifdef  __cplusplus
extern "C" {
#endif

int RSKSW_HMAC       (unsigned char *output, int *output_len, unsigned char *input, int input_len, unsigned char *key, int key_len, int macId);
int RSKSW_HMAC_SHA   (unsigned char *output, unsigned char *input, int input_len, unsigned char *key, int key_len);
int RSKSW_HMAC_Init  (unsigned char *key, int key_len, int macId);
int RSKSW_HMAC_Update(unsigned char *input, int input_len);
int RSKSW_HMAC_Final (unsigned char *output, int *output_len);


#ifdef  __cplusplus
}
#endif

#endif
