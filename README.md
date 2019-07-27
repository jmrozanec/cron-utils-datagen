# cron-utils-datagen
Provides means to create datasets for cron descriptions in various languages.


# TODO:
 - define a template file, where we can add all examples
  [ok]- template for EN
  - template for ES
  - template for SI
 [ok]- implement different dataset generation strategies:
  [ok]- directly from cron
  []- from cron template, without parameters replacement: is easier to learn a more generic cron?
  []- just replacing common things such as MIN1,MIN2,MIN3 for LIST
  [ok]- from cron description, with params replacement
  [ok]- from cron description, without params replacement
 []- define templates not included in MAIN, so that we can test generalization

 [ok]- make sure dataset generation is deterministic: under same conditions, same values are generated
 [wip]- train models over strategies and check how well do describe cron patterns