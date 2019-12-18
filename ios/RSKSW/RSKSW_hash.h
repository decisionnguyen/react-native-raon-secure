#ifndef __RSKSW_HASH_H_
#define __RSKSW_HASH_H_

#ifdef __cplusplus
extern "C" {
#endif

int RSKSW_HASH       (unsigned char *output, int *output_len, unsigned char *input, int input_len, int hashId);
int RSKSW_HASH_Init  (int hashId);
int RSKSW_HASH_Update(unsigned char *input,  int input_len);
int RSKSW_HASH_Final (unsigned char *output, int *output_len);

#ifdef __cplusplus
}
#endif

#endif
