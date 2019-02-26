#ifndef __ANDROID__

#ifndef __RSKSWPKCS10_H__
#define __RSKSWPKCS10_H__


typedef struct {
	/* Keys */
	unsigned char	pubkey[1024];
	int				pubkey_len;
	unsigned char	prikey[2048];
	int				prikey_len;
	char			passwd[32];

	/* subject */
	char	c[8];
	char	st[512];
	char	l[32];
	char	o[32];
	char	ou[64];
	char	cn[120];

	/* idn and vid random */
	unsigned char	random[20];

} RSKSW_PKCS10_CTX;

typedef struct {
	int		key_size;	/* byte */

} RSKSW_PKCS10_Option;

#ifdef  __cplusplus
extern "C" {
#endif

int
RSKSW_PKCS10_CertReq(ks_uint8 *out, int *out_len, ks_uint8 *key, int *key_len, char *c, char *st, char *l, char *o, char *ou, char *cn, int key_size);
int
RSKSW_PKCS10_Get_SubjectType(ks_uint8 *out, int subject_type, ks_uint8 *cert, int cert_len);
#ifdef  __cplusplus
}
#endif

#endif

#endif
