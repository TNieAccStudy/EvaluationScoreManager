from django.db import models



class BaseModel(models.Model):
    active = models.BooleanField(default=True)
    created_date = models.DateTimeField(auto_now_add=True)
    updated_date = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True


class User (BaseModel):
    first_name = models.CharField(max_length=255,null=False);
    last_name = models.CharField(max_length=255,null=False)
    username = models.CharField(max_length=60,null=False, unique=True)
    password = models.CharField(max_length=124, null=False)
    avatar = models.CharField(max_length=124, null=False)
    email = models.CharField(max_length=124, null=False)
    phone = models.CharField(max_length=11, null=False)

    class Meta:
        db_table = "user_info"


class Student(User):
    score = models.IntegerField()
    achievement = models.CharField(max_length=50)
    mssv = models.CharField(max_length=11, null=False)

    class Meta:
        db_table = "student"


class StudentAssistant(User):
    class Meta:
        db_table = "student_assistant"


class StudentAffairsOfficer(User):
    class Meta:
        db_table = "student_affairs_officer"


class Semester(models.Model):
    name = models.CharField(max_length=50, null=False)
    year = models.IntegerField(null=False)

    class Meta:
        db_table = "semester"


class Term(models.Model):
    name = models.CharField(max_length=255, null=False)
    max_value = models.IntegerField(null=False)

    class Meta:
        db_table = "term"


class ExtraActivity(BaseModel):
    bonus_score = models.IntegerField(null=False)
    title = models.CharField(max_length=255, null=False)
    description = models.TextField(null=True, blank=True)
    semester = models.ForeignKey(Semester, on_delete=models.DO_NOTHING, null=False)
    student_assistant = models.ForeignKey(StudentAssistant, models.DO_NOTHING, null=False)
    term = models.ForeignKey(Term, on_delete=models.DO_NOTHING, null=False)

    class Meta:
        db_table = "extra_activity"


class Bulletin(BaseModel):
    title = models.CharField(max_length=255, null=False)
    content = models.TextField(null=True)
    duration = models.DateTimeField(null=False)

    class Meta:
        db_table = "bulletin"


class ActivityBulletin(Bulletin):
    extra_activity = models.ForeignKey(ExtraActivity, on_delete=models.DO_NOTHING, null=True)

    class Meta:
        db_table = "activity_bulletin"


class SummaryBulletin(Bulletin):
    class Meta:
        db_table = "summary_bulletin"


class ActivityRegistry(BaseModel):
    student = models.ForeignKey(Student, models.DO_NOTHING, null=True)

    class Meta:
        db_table = "activity_registry"


class ActivityConfirmedAttendance(BaseModel):
    proofPicture = models.CharField(max_length=255, null=False)
    activity_registry = models.OneToOneField(ActivityRegistry, models.CASCADE, null=True)

    class Meta:
        db_table = "activity_confirmed_attendance"


class MissingActivity(BaseModel):
    proof_content = models.TextField(null=True)
    proof_picture = models.CharField(max_length=255, null=True)
    summary_bulletin = models.ForeignKey(SummaryBulletin, on_delete=models.DO_NOTHING, null=False)
    activity_registry = models.ForeignKey(ActivityRegistry, models.DO_NOTHING, null=False)
    missing_activity = models.OneToOneField(ActivityConfirmedAttendance, on_delete=models.CASCADE, null=True)
    executed_status = models.CharField(max_length=50, null=False)
    student_assistant = models.ForeignKey(StudentAssistant, models.DO_NOTHING, null=True)

    class Meta:
        db_table = "missing_activity"


class CancelRequirement(BaseModel):
    reason = models.CharField(max_length=255, null=True)
    reason_detail = models.TextField(null=True)
    executed_status = models.CharField(max_length=50, null=False)
    student_affairs_officer = models.ForeignKey(StudentAffairsOfficer, models.DO_NOTHING, null=True)

    class Meta:
        db_table = "cancel_requirement"


class CancelBulletinRequirement(CancelRequirement):
    bulletin = models.ForeignKey(Bulletin, models.DO_NOTHING, null=False)

    class Meta:
        db_table = "cancel_bulletin_requirement"


class CancelActivityRequirement(CancelRequirement):
    extra_activity = models.ForeignKey(ExtraActivity, models.DO_NOTHING, null=False)

    class Meta:
        db_table = "cancel_activity_requirement"


class Interaction(BaseModel):
    bulletin = models.ForeignKey(Bulletin, models.DO_NOTHING, null=False)
    student = models.ForeignKey(Student, models.CASCADE, null=False)

    class Meta:
        db_table = "interaction"

class Comment(Interaction):
    content = models.TextField()

    class Meta:
        db_table = "comment"


class Reactions(Interaction):
    type = models.CharField(max_length=50, null=False)
    
    class Meta:
        db_table = "reactions"

