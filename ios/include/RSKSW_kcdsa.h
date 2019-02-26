#ifndef _RSKSW_KCDSA_H_
#define _RSKSW_KCDSA_H_

#include "RSKSW_bigint.h"
#include "RSKSW_define.h"

#ifdef __ANDROID__
#include "../cmvp/include/ks_asymmetry.h"
#else
#include "ks_asymmetry.h"
#endif

/**
 *	Prototypes
 */

#ifdef __cplusplus
extern "C" {
#endif

KS_KCDSA_Parameters *
			RSKSW_KCDSA_Parameters_New			(void);

void		RSKSW_KCDSA_Parameters_Free		(KS_KCDSA_Parameters *params);

int	RSKSW_KCDSA_Parameters_Decode		(KS_KCDSA_Parameters *params,
											  KSW_BYTE *input,
											  KSW_WORD inputLength);

#ifdef __cplusplus
}
#endif

#endif
