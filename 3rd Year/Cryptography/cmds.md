## Using noweb and pandoc

For extracting the code from the md file

```bash
notangle filename.md > filename.py
```
Where there needs to be a <<\*>> root module, otherwise just add -Rmodule_name after notangle


For transforming the md file into pdf 

```bash
pandoc -t latex -o filename.pdf filename.md
```

## Encrypting

- https://www.howtogeek.com/427982/how-to-encrypt-and-decrypt-files-with-gpg-on-linux/

Simple encryption using a known key

```bash
gpg --encrypt -r mail_with_public_key@mail.com filename
```

Decryption

```bash
gpg --decrypt filename.gpg > filename.txt
```