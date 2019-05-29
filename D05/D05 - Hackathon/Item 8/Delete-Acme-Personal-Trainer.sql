start transaction;

use `Acme-Personal-Trainer`;

revoke all privileges on `Acme-Personal-Trainer`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Personal-Trainer`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Personal-Trainer`;

commit;