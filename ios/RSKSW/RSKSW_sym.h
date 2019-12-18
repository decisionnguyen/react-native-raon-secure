#ifndef __RSKSW_SYM_H_
#define __RSKSW_SYM_H_

#define	RSKSW_SYM_PADDING_NO        KS_SYM_PADDING_NO
#define	RSKSW_SYM_PADDING_ZERO      KS_SYM_PADDING_ZERO	// for CBCMAC
#define	RSKSW_SYM_PADDING_HASH      KS_SYM_PADDING_HASH
#define	RSKSW_SYM_PADDING_PKCS      KS_SYM_PADDING_PKCS

#ifdef __ANDROID__
#include "../cmvp/include/ks_cmvp.h"
#else
#include "ks_cmvp.h"
#endif

#ifdef  __cplusplus
extern "C" {
#endif


int RSKSW_SYM_GEN_SECRET_KEY(unsigned char *output, int *output_len, int alg_id);
int RSKSW_SYM_ENCRYPT(unsigned char *output, int *output_len, unsigned char *input, int input_len, unsigned char *key, int key_len, unsigned char *iv, int iv_len, int alg_id, int mode_id, int padding_id);
int RSKSW_SYM_DECRYPT(unsigned char *output, int *output_len, unsigned char *input, int input_len, unsigned char *key, int key_len, unsigned char *iv, int iv_len, int alg_id, int mode_id, int padding_id);

/*  RSKSW_SYM_ENCRYPT_INIT
    seedCtx[out] : enc 시작-끝까지 유지되야 함.
    나머지는 in.
 */
int RSKSW_SYM_ENCRYPT_INIT(KSC_SYM_CTX *seedCtx, unsigned char *key, int key_len, unsigned char *iv, int iv_len, int alg_id, int mode_id, int padding_id);
int RSKSW_SYM_ENCRYPT_UPDATE(KSC_SYM_CTX *seedCtx, unsigned char *output, unsigned int *output_len, unsigned char *input, int input_len);
int RSKSW_SYM_ENCRYPT_FINAL(KSC_SYM_CTX *seedCtx, unsigned char *output, unsigned int *output_len);

/*  RSKSW_SYM_DECRYPT_INIT
 seedCtx[out] : enc 시작-끝까지 유지되야 함.
 나머지는 in.
 */
int RSKSW_SYM_DECRYPT_INIT(KSC_SYM_CTX *seedCtx, unsigned char *key, int key_len, unsigned char *iv, int iv_len, int alg_id, int mode_id, int padding_id);
int RSKSW_SYM_DECRYPT_UPDATE(KSC_SYM_CTX *seedCtx, unsigned char *output, unsigned int *output_len, unsigned char *input, int input_len);
int RSKSW_SYM_DECRYPT_FINAL(KSC_SYM_CTX *seedCtx, unsigned int *output_len);//return padding Len
#ifdef  __cplusplus
}
#endif


#endif
