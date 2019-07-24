# cron-utils-datagen
Provides means to create datasets for cron descriptions in various languages.


# TODO:
 - define a template file, where we can add all examples
  [ok]- template for EN
  - template for ES
  - template for SI
 - implement different dataset generation strategies:
  - directly from cron
  - from cron description
  [ok]- from cron description with replacements

 [ok]- make sure dataset generation is deterministic: under same conditions, same values are generated
 - train models over strategies and check how well do describe cron patterns
