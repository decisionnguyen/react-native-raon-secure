
#ifndef __RSKSWSEED_H_
#define __RSKSWSEED_H_

#include "RSKSW_define.h"
#include "RSKSW_sym.h"

/**
 *	Constants
 */

#define	SEED_KEY_SIZE				16
#define	SEED_IV_SIZE				16

/**
 *	Error codes
 */

/**
 *	Prototypes
 */

#ifdef __cplusplus
extern "C" {
#endif

int  RSKSW_SEED_CBC_Encrypt		(KSW_BYTE *psOutput, KSW_BYTE *psInput, KSW_WORD nInputLength,
									 KSW_BYTE *psIV, KSW_BYTE *psKey);
int  RSKSW_SEED_CBC_Decrypt		(KSW_BYTE *psOutput, KSW_BYTE *psInput, KSW_WORD nInputLength,
									 KSW_BYTE *psIV, KSW_BYTE *psKey);
    
    
int RSKSW_SEED_CBC_EncryptInit(KSW_BYTE *psIV,
                               KSW_BYTE *psKey, int paddingId);

int RSKSW_SEED_CBC_EncryptUpdate(unsigned char *output, unsigned char *input, int input_len);
/* @return : last block with padding */
int RSKSW_SEED_CBC_EncryptFinal(unsigned char *output);
    
int RSKSW_SEED_CBC_DecryptInit(KSW_BYTE *psIV,
                               KSW_BYTE *psKey, int paddingId);

int RSKSW_SEED_CBC_DecryptUpdate(unsigned char *output, unsigned char *input, int input_len);
/* @return : padding length */
int RSKSW_SEED_CBC_DecryptFinal();

#ifdef __cplusplus
}
#endif

#endif
