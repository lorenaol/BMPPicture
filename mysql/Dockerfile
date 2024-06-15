# Use the specified base image
FROM critoma/linux-u20-dev-security-ism

# Install MySQL
RUN apt-get update && \
    DEBIAN_FRONTEND="noninteractive" apt-get install -y mysql-server

# Copy MySQL configuration file if needed (e.g., to set custom configurations)
# COPY mysql.conf /etc/mysql/mysql.conf.d/mysqld.cnf

ENV MYSQL_DATABASE=db \
    MYSQL_USER=user \
    MYSQL_PASSWORD=pass \
    MYSQL_ROOT_PASSWORD=pass

# Expose MySQL default port
EXPOSE 3306

# Start MySQL service on container startup
CMD ["mysqld", "--default-authentication-plugin=mysql_native_password"]