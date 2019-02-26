//
//	RSKSW_des.h
//

#ifndef __RSKSW_DES_H_
#define __RSKSW_DES_H_

#include "RSKSW_define.h"

/**
 *	Constants
 */

#define	RSKSW_DES_DIR_ENCRYPT					0
#define	RSKSW_DES_DIR_DECRYPT					1

#define	RSKSW_DES_KEY_SIZE						8		// BYTE

#define	RSKSW_DES_BLOCK_SIZE					8		// BYTE
#define	RSKSW_DES_IV_SIZE						8		// BYTE

/**
 *	Struct
 */

typedef struct {
	KSW_WORD		roundKey[32];
} RSKSW_DES_KEY;

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

int			RSKSW_DES_IsWeakKey		(const KSW_BYTE key[8]);

void		RSKSW_DES_MakeKey			(RSKSW_DES_KEY *desKey, const KSW_BYTE key[8],
									 const int dir);

void		RSKSW_DES_IP				(KSW_WORD *left, KSW_WORD *right);
void		RSKSW_DES_Core				(KSW_WORD *left, KSW_WORD *right, const RSKSW_DES_KEY *desKey);
void		RSKSW_DES_FP				(KSW_WORD *left, KSW_WORD *right);

void		RSKSW_DES_Main				(KSW_BYTE output[8], const KSW_BYTE input[8],
									 const RSKSW_DES_KEY *desKey);

// mode

int			RSKSW_DES_ECB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE key[8], const int dir);

int			RSKSW_DES_CBC_MAC				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE key[8], KSW_BYTE iv[8], const int dir);

int			RSKSW_DES_CFB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE key[8], KSW_BYTE iv[8], 
									 const KSW_WORD modeSize, const int dir);

int			RSKSW_DES_OFB				(KSW_BYTE *output,
									 const KSW_BYTE *input, const KSW_WORD inputLength,
									 const KSW_BYTE key[8], KSW_BYTE iv[8], 
									 const KSW_WORD modeSize, const int dir);


int			RSKSW_DES_MAC				(KSW_BYTE *output,
									   const KSW_BYTE *input, 
									   const KSW_WORD nInputLength,
									   const KSW_BYTE key[8], 
									   KSW_BYTE iv[8]);
#ifdef __cplusplus
}
#endif

#endif
