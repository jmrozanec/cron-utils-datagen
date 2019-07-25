# cron-utils-datagen
Provides means to create datasets for cron descriptions in various languages.


# TODO:
 - define a template file, where we can add all examples
  [ok]- template for EN
  - template for ES
  - template for SI
 [ok]- implement different dataset generation strategies:
  [ok]- directly from cron
  [ok]- from cron description, with params replacement
  [ok]- from cron description, without params replacement

 [ok]- make sure dataset generation is deterministic: under same conditions, same values are generated
 [wip]- train models over strategies and check how well do describe cron patterns