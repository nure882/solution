const translations = {
  en: {
    app_name: 'GasStation',
    nav_dashboard: 'Dashboard', nav_stations: 'Stations', nav_gas_types: 'Fuel Types',
    nav_pumps: 'Pumps', nav_coupons: 'Coupons', nav_users: 'Users',
    nav_profile: 'Profile', nav_signout: 'Sign Out',

    auth_signin_title: 'Sign in', auth_signin_subtitle: 'Enter your credentials to continue.',
    auth_register_title: 'Create account', auth_register_subtitle: 'Fill in the details below to get started.',
    auth_email: 'Email', auth_password: 'Password', auth_full_name: 'Full Name',
    auth_signin_btn: 'Sign In', auth_create_account: 'Create Account',
    auth_new_here: 'No account?', auth_already_have: 'Already have an account?',
    auth_error_empty: 'Please fill in all fields', auth_error_invalid: 'Invalid email or password',
    auth_error_register: 'Registration failed. Email may already be in use.',

    dash_title: 'Dashboard', dash_welcome: 'Welcome',
    dash_role: 'Role', dash_open: 'Open',

    common_search: 'Search…', common_add: 'Add', common_edit: 'Edit', common_delete: 'Delete',
    common_save: 'Save', common_cancel: 'Cancel', common_actions: 'Actions',
    common_view: 'View', common_back: 'Back', common_loading: 'Loading…',
    common_empty: 'Nothing here yet', common_confirm_delete_title: 'Confirm deletion',
    common_confirm_delete_msg: 'This action cannot be undone. Continue?',

    station_city: 'City', station_address: 'Address', station_work_time: 'Work time',
    station_description: 'Description', station_equipment: 'Equipment',
    stations_title: 'Stations', station_new: 'New station', station_edit: 'Edit station',
    station_pumps: 'Fuel pumps',

    gas_type_name: 'Name', gas_types_title: 'Fuel types', gas_type_new: 'New fuel type', gas_type_edit: 'Edit fuel type',

    pump_number: 'Number', pump_gas_count: 'Fuel left (L)', pump_cost: 'Price (per L)',
    pump_station: 'Station', pump_gas_type: 'Fuel type',
    pumps_title: 'Pumps', pump_new: 'New pump', pump_edit: 'Edit pump',

    coupon_customer: 'Customer', coupon_gas_type: 'Fuel type', coupon_liters: 'Liters',
    coupon_expiration: 'Expires', coupon_active: 'Active', coupon_used: 'Used',
    coupons_title: 'Coupons', coupon_new: 'New coupon', coupon_edit: 'Edit coupon',

    user_email: 'Email', user_name: 'Full name', user_role: 'Role', user_status: 'Status',
    users_title: 'Users', user_new: 'New user', user_password: 'Password',
    user_banned: 'Banned', user_active: 'Active', user_ban: 'Ban', user_unban: 'Unban',

    profile_title: 'Profile',
  },
  uk: {
    app_name: 'GasStation',
    nav_dashboard: 'Панель', nav_stations: 'Станції', nav_gas_types: 'Типи пального',
    nav_pumps: 'Колонки', nav_coupons: 'Талони', nav_users: 'Користувачі',
    nav_profile: 'Профіль', nav_signout: 'Вийти',

    auth_signin_title: 'Вхід', auth_signin_subtitle: 'Введіть облікові дані для продовження.',
    auth_register_title: 'Реєстрація', auth_register_subtitle: 'Заповніть поля нижче, щоб почати.',
    auth_email: 'Електронна пошта', auth_password: 'Пароль', auth_full_name: 'Повне ім\u2019я',
    auth_signin_btn: 'Увійти', auth_create_account: 'Створити акаунт',
    auth_new_here: 'Немає акаунта?', auth_already_have: 'Вже є акаунт?',
    auth_error_empty: 'Заповніть усі поля', auth_error_invalid: 'Невірний email або пароль',
    auth_error_register: 'Помилка реєстрації. Можливо, email уже використовується.',

    dash_title: 'Панель керування', dash_welcome: 'Вітаємо',
    dash_role: 'Роль', dash_open: 'Відкрити',

    common_search: 'Пошук…', common_add: 'Додати', common_edit: 'Редагувати', common_delete: 'Видалити',
    common_save: 'Зберегти', common_cancel: 'Скасувати', common_actions: 'Дії',
    common_view: 'Перегляд', common_back: 'Назад', common_loading: 'Завантаження…',
    common_empty: 'Поки що порожньо', common_confirm_delete_title: 'Підтвердження видалення',
    common_confirm_delete_msg: 'Цю дію не можна скасувати. Продовжити?',

    station_city: 'Місто', station_address: 'Адреса', station_work_time: 'Режим роботи',
    station_description: 'Опис', station_equipment: 'Обладнання',
    stations_title: 'Автозаправні станції', station_new: 'Нова станція', station_edit: 'Редагувати станцію',
    station_pumps: 'Паливні колонки',

    gas_type_name: 'Назва', gas_types_title: 'Типи пального', gas_type_new: 'Новий тип пального', gas_type_edit: 'Редагувати тип пального',

    pump_number: 'Номер', pump_gas_count: 'Залишок (л)', pump_cost: 'Ціна (за л)',
    pump_station: 'Станція', pump_gas_type: 'Тип пального',
    pumps_title: 'Паливні колонки', pump_new: 'Нова колонка', pump_edit: 'Редагувати колонку',

    coupon_customer: 'Клієнт', coupon_gas_type: 'Тип пального', coupon_liters: 'Літри',
    coupon_expiration: 'Діє до', coupon_active: 'Активний', coupon_used: 'Використано',
    coupons_title: 'Знижкові талони', coupon_new: 'Новий талон', coupon_edit: 'Редагувати талон',

    user_email: 'Email', user_name: 'Повне ім\u2019я', user_role: 'Роль', user_status: 'Статус',
    users_title: 'Користувачі', user_new: 'Новий користувач', user_password: 'Пароль',
    user_banned: 'Заблоковано', user_active: 'Активний', user_ban: 'Заблокувати', user_unban: 'Розблокувати',

    profile_title: 'Профіль',
  },
};

export default translations;
