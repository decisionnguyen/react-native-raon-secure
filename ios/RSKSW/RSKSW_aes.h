#ifndef __RSKSW_AES_H_
#define __RSKSW_AES_H_

#include "RSKSW_define.h"

#define	RSKSW_AES_KEY_SIZE				16
#define	RSKSW_AES_IV_SIZE					16

#ifdef __cplusplus
extern "C" {
#endif


int RSKSW_AES_CBC_Encrypt(unsigned char *output, unsigned char *input, int input_len, unsigned char *key, int key_len, unsigned char *iv, int iv_len);
int RSKSW_AES_CBC_Decrypt(unsigned char *output, unsigned char *input, int input_len, unsigned char *key, int key_len, unsigned char *iv, int iv_len);


#ifdef __cplusplus
} // of extern "C"
#endif

#endif

