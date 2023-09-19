# Generated by Django 4.2.5 on 2023-09-12 07:49

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='BeakjoonUser',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('bj_id', models.CharField(max_length=100)),
                ('solved_count', models.IntegerField()),
                ('bj_class', models.IntegerField()),
                ('tier', models.IntegerField()),
                ('rating', models.IntegerField()),
                ('rating_by_problem_sum', models.IntegerField()),
                ('rating_by_class', models.IntegerField()),
                ('rating_by_solved', models.IntegerField()),
                ('rank', models.IntegerField()),
            ],
            options={
                'db_table': 'beakjoon_user',
            },
        ),
        migrations.CreateModel(
            name='Problem',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=100)),
                ('problem_level', models.IntegerField()),
                ('sovled_user_count', models.IntegerField()),
                ('problem_point', models.IntegerField()),
                ('users', models.ManyToManyField(to='recommend.beakjoonuser')),
            ],
            options={
                'db_table': 'problem',
            },
        ),
        migrations.CreateModel(
            name='Tag',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('tag_name', models.CharField(max_length=255)),
                ('problems', models.ManyToManyField(to='recommend.problem')),
            ],
            options={
                'db_table': 'tag',
            },
        ),
    ]