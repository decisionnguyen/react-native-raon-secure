//
//	RSKSW_tdes.h
//

#ifndef __RSKSW_TDES_H_
#define __RSKSW_TDES_H_

#include "RSKSW_des.h"

/**
 *	Constants
 */

#define	RSKSW_TDES_DIR_ENCRYPT					RSKSW_DES_DIR_ENCRYPT
#define	RSKSW_TDES_DIR_DECRYPT					RSKSW_DES_DIR_DECRYPT

#define	RSKSW_TDES_MAX_KEY_SIZE				(RSKSW_DES_KEY_SIZE * 3)
#define	RSKSW_TDES_MIN_KEY_SIZE				RSKSW_DES_KEY_SIZE

#define	RSKSW_TDES_BLOCK_SIZE					RSKSW_DES_BLOCK_SIZE
#define	RSKSW_TDES_IV_SIZE						RSKSW_DES_IV_SIZE

/**
 *	Struct
 */

typedef struct {
	RSKSW_DES_KEY		key1;
	RSKSW_DES_KEY		key2;
	RSKSW_DES_KEY		key3;
} RSKSW_TDES_KEY;

/**
 *	Error codes
 */

//	KS_COMMON_ERROR_INVALID_INPUT

/**
 *	Prototypes
 */

#ifdef __cplusplus
extern "C" {
#endif

int			RSKSW_TDES_IsWeakKey		(const KSW_BYTE *key, const KSW_WORD keyLength);

int			RSKSW_TDES_MakeKey			(RSKSW_TDES_KEY *tdesKey, const KSW_BYTE *key, 
									 const KSW_WORD keyLength, const int dir);

void		RSKSW_TDES_Main			(KSW_BYTE output[8], const KSW_BYTE input[8], 
									 const RSKSW_TDES_KEY *tdesKey);

// mode

int			RSKSW_TDES_ECB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE *key, const KSW_WORD keyLength,
									 const int dir);

int			RSKSW_TDES_CBC				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE *key, const KSW_WORD keyLength,
									 KSW_BYTE iv[8], const int dir);

int			RSKSW_TDES_CFB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE *key, const KSW_WORD keyLength,
									 KSW_BYTE iv[8], const KSW_WORD modeSize, const int dir);

int			RSKSW_TDES_OFB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE *key, const KSW_WORD keyLength,
									 KSW_BYTE iv[8], const KSW_WORD modeSize, const int dir);


#ifdef __cplusplus
}
#endif

#endif
